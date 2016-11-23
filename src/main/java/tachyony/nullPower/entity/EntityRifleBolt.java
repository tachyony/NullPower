/*
 * Copyright (C) 2014 Tachyony
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tachyony.nullPower.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tachyony.nullPower.NullPower;

/**
 *
 */
public class EntityRifleBolt extends EntityThrowable implements IProjectile {
    private static final DataParameter<Byte> CRITICAL = EntityDataManager.<Byte>createKey(EntityRifleBolt.class, DataSerializers.BYTE);
    
	/** Private fields from EntityArrow are now protected instead */
	protected int xTile = -1, yTile = -1, zTile = -1, inData;

	protected Block inTile;
	
	private int ticksInGround = 0, ticksInAir = 0;
    
	/** damage and knockback have getters and setters, so can be private */
    private double damage = 6.0D;
    
    /** The amount of knockback an arrow applies when it hits a mob. */
    private int knockbackStrength;
    
    /** 1 if the player can pick up the arrow */
    public int canBePickedUp;
    
    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake;
    
    /** The owner of this arrow. */
    public Entity shootingEntity;
    
	/** Basic constructor is necessary
	 * @param world 
    */
	public EntityRifleBolt(World world) {
		super(world);
        //this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }
    
	/** Constructs an arrow at a position, but with no heading or velocity
	 * @param world 
	 * @param x 
	 * @param y 
	 * @param z 
     */
	public EntityRifleBolt(World world, double x, double y, double z) {
		super(world, x, y, z);
        this.setSize(0.5F, 0.5F);
        this.setPosition(x, y, z);
    }
    
	/** Constructs an arrow with heading based on shooter and velocity, modified by the arrow's velocityFactor
	 * @param world 
	 * @param shooter 
	 * @param velocity 
     */
	public EntityRifleBolt(World world, EntityLivingBase shooter, float velocity) {
		super(world);
		this.shootingEntity = shooter;
		if (shooter instanceof EntityPlayer) {
            this.canBePickedUp = 1;
        }

		setSize(0.5F, 0.5F);
		setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(this.posX, this.posY, this.posZ);
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
		setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity * getVelocityFactor(), 1.0F);
    }
    
    /**
	 * Constructs an arrow heading towards target's initial position with given
	 * velocity, but abnormal Y trajectory;
	 * @param world 
	 * @param shooter 
	 * @param target 
	 * @param velocity 
	 * @param wobble amount of deviation from base trajectory, used by Skeletons
	 *            and the like; set to 0.0F for no x/z deviation
     */
	public EntityRifleBolt(World world, EntityLivingBase shooter, EntityLivingBase target, float velocity, float wobble) {
		super(world);
        this.shootingEntity = shooter;
        if (shooter instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.posY = shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D;
        double d0 = target.posX - shooter.posX;
        double d1 = target.getEntityBoundingBox().minY + target.height / 3.0F - this.posY;
        double d2 = target.posZ - shooter.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(shooter.posX + d4, this.posY, shooter.posZ + d5, f2, f3);
            float f4 = (float)d3 * 0.2F;
            this.setThrowableHeading(d0, d1 + f4, d2, velocity, wobble);
        }
    }
    
    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            onImpactEntity(result);
        } else {
            onImpactBlock(result);
        }
    }
	
    @Override
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;
        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }
        
        d0 = d0 * 64 * getRenderDistanceWeight();
        return distance < d0 * d0;
    }
    
    @Override
    protected void entityInit()
    {
		super.entityInit();
		this.dataManager.register(CRITICAL, Byte.valueOf((byte)0));
    }
    
    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void setVelocity(double x, double y, double z)
    {
	    this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(x * x + z * z);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }
    
    /**
    * Gets the amount of gravity to apply to the thrown entity with each tick.
    * @return Velocity
    */
    @Override
    protected float getGravityVelocity()
    {
        return 0.003F;
    }

	@Override
	public void onUpdate() {
		// This calls the Entity class' update method directly, circumventing EntityArrow
		super.onEntityUpdate();
		updateAngles();
		checkInGround();
		if (this.arrowShake > 0) {
			--this.arrowShake;
		}
		
		if (this.inGround) {
			updateInGround();
		} else {
			updateInAir();
		}
	}
	
	/**
     * Called by a player entity when they collide with an entity
     */
    @Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
			boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && player.capabilities.isCreativeMode;
			if (this.canBePickedUp == 1 && !player.inventory.addItemStackToInventory(new ItemStack(NullPower.rifleAmmo, 1, 0))) {
                flag = false;
            }

			if (flag) {
				playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				player.onItemPickup(this, 1);
				setDead();
            }
        }
    }
    
    /** Sets the amount of damage the arrow will inflict when it hits a mob 
	 * @param value Damage
	 */
	public void setDamage(double value) {
		this.damage = value;
    }
    
	/** Sets the amount of knockback the arrow applies when it hits a mob.
	 * @param value Strength
     */
	public void setKnockbackStrength(int value) {
		this.knockbackStrength = value;
    }
    
    /** Returns the amount of knockback the arrow applies when it hits a mob
	 * @return Strength
	 */
	public int getKnockbackStrength() {
		return this.knockbackStrength;
	}
	
	/**
	 * Updates yaw and pitch based on current motion
	 */
	protected void updateAngles() {
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ
					* this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX,
					this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY,
					f) * 180.0D / Math.PI);
		}
	}
	
	/**
	 * Updates the arrow's position and angles
	 */
	protected void updatePosition() {
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / Math.PI); this.rotationPitch
				- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			//;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch)
				* 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float motionFactor = 0.99F;

		if (isInWater()) {
			for (int i = 0; i < 4; ++i) {
				float f3 = 0.25F;
				this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ
					* f3, this.motionX, this.motionY, this.motionZ);
			}

			motionFactor = 0.8F;
		}

		updateMotion(motionFactor, getGravityVelocity());
		setPosition(this.posX, this.posY, this.posZ);
	}
	
	/**
     * Adjusts arrow's motion: multiplies each by factor, subtracts adjustY from
     * motionY
     * @param factor 
     * @param adjustY 
     */
    protected void updateMotion(float factor, float adjustY) {
        this.motionX *= factor;
        this.motionY *= factor;
        this.motionZ *= factor;
        this.motionY -= adjustY;
    }
    
    /**
     * Checks if entity is colliding with a block and if so, sets inGround to
     * true
     */
    @SuppressWarnings("unused")
    protected void checkInGround() {
		BlockPos blockPos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState iBlockState = this.worldObj.getBlockState(blockPos);
		Block i = iBlockState.getBlock();
		if (iBlockState != Material.AIR) {
		    //???i.setBlockBoundsBasedOnState(this.worldObj, blockPos);
			AxisAlignedBB axisalignedbb = iBlockState.getCollisionBoundingBox(this.worldObj, blockPos);
			if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockPos).isVecInside(new Vec3d(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
			}
        }
    }
    
    /**
	 * If entity is in ground, updates ticks in ground or adjusts position if
	 * block no longer in world
	 */
	protected void updateInGround() {
	    BlockPos blockPos = new BlockPos(this.xTile, this.yTile, this.zTile);
	    IBlockState iblockstate = this.worldObj.getBlockState(blockPos);
		Block block = iblockstate.getBlock();
		int j = block.getMetaFromState(iblockstate);

		if (block == this.inTile && j == this.inData) {
			++this.ticksInGround;
			if (this.ticksInGround == 1200) {
				setDead();
			}
		} else {
			this.inGround = false;
			this.motionX *= this.rand.nextFloat() * 0.2F;
			this.motionY *= this.rand.nextFloat() * 0.2F;
			this.motionZ *= this.rand.nextFloat() * 0.2F;
			this.ticksInGround = 0;
			this.ticksInAir = 0;
		}
	}
	
	/** 
	 * Checks for impacts, spawns trailing particles and updates entity position
	 */
	protected void updateInAir() {
		++this.ticksInAir;
		RayTraceResult mop = checkForImpact();
		if (mop != null) {
			onImpact(mop);
		}
		
		spawnTrailingParticles();
		updatePosition();
		doBlockCollisions();
	}
	
	/** Returns the arrow's velocity factor
	 * @return Velocity
	 */
	@SuppressWarnings("static-method")
	protected float getVelocityFactor() {
		return 1.5F;
	}

	/** The name of the particle to spawn for trailing particle effects
     * @return Particle
     */
    @SuppressWarnings("static-method")
    protected EnumParticleTypes getParticleName() {
        return EnumParticleTypes.CRIT;
    }

    /**
     * Returns whether trailing particles should spawn (vanilla returns
     * isCritical())
     * @return Should
     */
    protected boolean shouldSpawnParticles() {
        return (getIsCritical() && getParticleName().getParticleName().length() > 0);
    }
    
    /**
     * Spawns trailing particles, if any
     */
    protected void spawnTrailingParticles() {
        if (shouldSpawnParticles()) {
            for (int i = 0; i < 4; ++i) {
				this.worldObj.spawnParticle(getParticleName(), this.posX + this.motionX* i / 4.0D,
					this.posY + this.motionY * i / 4.0D, this.posZ + this.motionZ * i / 4.0D, -this.motionX, -this.motionY + 0.2D,
					-this.motionZ);
            }
        }
    }
    
    /**
	 * Returns MovingObjectPosition of Entity or Block impacted, or null if
	 * nothing was struck
	 * @return Position
	 */
	protected RayTraceResult checkForImpact() {
		Vec3d start = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d end = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult mop = this.worldObj.rayTraceBlocks(start, end, false, true, false);
		start = new Vec3d(this.posX, this.posY, this.posZ);
		end = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		if (mop != null) {
			end = new Vec3d(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		}

		Entity entity = null;
		@SuppressWarnings("rawtypes")
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				this,
				this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D));
		double d0 = 0.0D;
		double hitBox = 0.30000001192092896D;

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = (Entity) list.get(i);
			if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(hitBox);
				RayTraceResult rayTraceResult = axisalignedbb.calculateIntercept(start, end);
				if (rayTraceResult != null) {
					double d1 = start.distanceTo(rayTraceResult.hitVec);
					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}

		if (entity != null) {
			mop = new RayTraceResult(entity);
		}

		if (mop != null && mop.entityHit != null && mop.entityHit instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)mop.entityHit;
			if (player.capabilities.disableDamage||
			        (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(player))) {
				mop = null;
			}
		}

		return mop;
	}
	
	/**
     * Called when custom arrow impacts another entity
     * @param mop 
     */
    protected void onImpactEntity(RayTraceResult result) {
        float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        int i = MathHelper.ceiling_double_int((double)f * this.damage);
        if (this.getIsCritical())
        {
            i += this.rand.nextInt(i / 2 + 2);
        }
        
        DamageSource damageSource;
        if (this.shootingEntity == null)
        {
            damageSource = DamageSource.causeThrownDamage(this, this);
        }
        else
        {
            damageSource = DamageSource.causeThrownDamage(this, this.shootingEntity);
        }
        
		if (isBurning() && !(result.entityHit instanceof EntityEnderman)) {
		    result.entityHit.setFire(5);
        }

        if (result.entityHit.attackEntityFrom(damageSource, (float)i)) {
            if (result.entityHit instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase)result.entityHit;
                if (!this.worldObj.isRemote) {
                    entityLivingBase.setArrowCountInEntity(entityLivingBase.getArrowCountInEntity() + 1);
                }

                if (this.knockbackStrength > 0) {
                    float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    if (f3 > 0.0F) {
                        // Translate knockback maths to something sane
                        double knockback = this.knockbackStrength * 0.6000000238418579D / f3;
                        entityLivingBase.addVelocity(this.motionX * knockback, 0.1D, this.motionZ * knockback);
                    }
                }

                if (this.shootingEntity instanceof EntityLivingBase) {
                    EnchantmentHelper.applyThornEnchantments(entityLivingBase, this.shootingEntity);
                    EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entityLivingBase);
                }
                
                if (this.shootingEntity != null && entityLivingBase != this.shootingEntity && entityLivingBase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                	((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                }
            }

            playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            setDead();
        } else {
            this.motionX *= -0.10000000149011612D;
            this.motionY *= -0.10000000149011612D;
            this.motionZ *= -0.10000000149011612D;
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            this.ticksInAir = 0;
		}
    }
    
    /**
	 * Called when custom arrow impacts a block
	 * @param mop 
	 */
	protected void onImpactBlock(RayTraceResult result) {
	    BlockPos blockPos = result.getBlockPos();
		this.xTile = blockPos.getX();
		this.yTile = blockPos.getY();
		this.zTile = blockPos.getZ();
		IBlockState iBlockState = this.worldObj.getBlockState(blockPos);
		this.inTile = iBlockState.getBlock();
		this.inData = this.inTile.getMetaFromState(iBlockState);
		this.motionX = (double)((float)(result.hitVec.xCoord - this.posX));
		this.motionY = (double)((float)(result.hitVec.yCoord - this.posY));
		this.motionZ = (double)((float)(result.hitVec.zCoord - this.posZ));
		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
		this.posX -= this.motionX / f2 * 0.05000000074505806D;
		this.posY -= this.motionY / f2 * 0.05000000074505806D;
		this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
		this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
		this.inGround = true;
		this.arrowShake = 7;
		setIsCritical(false);
		if (iBlockState.getMaterial() != Material.AIR) {
			this.inTile.onEntityCollidedWithBlock(this.worldObj, blockPos, iBlockState, this);
		}
	}

	/**
	 * Super must be called in order for arrow's flight path to be correct,
	 * despite the otherwise wasteful duplication of labour
     */ //EntityArrow
    @Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setShort("xTile", (short) this.xTile);
		compound.setShort("yTile", (short) this.yTile);
		compound.setShort("zTile", (short) this.zTile);
		compound.setShort("life", (short)this.ticksInGround);
		ResourceLocation resourceLocation = (ResourceLocation)Block.REGISTRY.getNameForObject(this.inTile);
		compound.setString("inTile", resourceLocation == null ? "": resourceLocation.toString());
		compound.setByte("inData", (byte)this.inData);
		compound.setByte("shake", (byte)this.arrowShake);
		compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		compound.setByte("pickup", (byte)this.canBePickedUp);
		compound.setDouble("damage", this.damage);
	}
    
    /**
	 * Super must be called in order for arrow's flight path to be correct,
	 * despite the otherwise wasteful duplication of labour
     */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.xTile = compound.getShort("xTile");
		this.yTile = compound.getShort("yTile");
		this.zTile = compound.getShort("zTile");
		this.ticksInGround = compound.getShort("life");
		if (compound.hasKey("inTile", 8))
		{
		    this.inTile = Block.getBlockFromName(compound.getString("inTile"));
		}
		else
		{
		    this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		}
		
		this.inData = compound.getByte("inData") & 255;
		this.arrowShake = compound.getByte("shake") & 255;
		this.inGround = compound.getByte("inGround") == 1;
		
		if (compound.hasKey("damage", 99))
        {
		    this.damage = compound.getDouble("damage");
        }
        
		if (compound.hasKey("pickup", 99))
        {
            this.canBePickedUp = compound.getByte("pickup");
        }
        else if (compound.hasKey("player", 99))
        {
            this.canBePickedUp = compound.getBoolean("player") ? 1 : 0;
        }
	}

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     * @return Is critical
     */
    public boolean getIsCritical()
    {
        Byte b0 = ((Byte)this.dataManager.get(CRITICAL)).byteValue();
        return (b0 & 1) != 0;
    }
    
    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     * @param par1 Is critical
     */
    public void setIsCritical(boolean critical)
    {
        Byte b0 = ((Byte)this.dataManager.get(CRITICAL)).byteValue();
        if (critical)
        {
            this.dataManager.set(CRITICAL, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(CRITICAL, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    @Override
    public boolean canBeAttackedWithItem()
    {
        // Should actually be false
        return false;
    }
    
    public float getEyeHeight()
    {
        // Always 0
        return 0.0F;
    }
    
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }
    
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    @Override
    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= f2;
        par3 /= f2;
        par5 /= f2;
        par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
        par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
        par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
        par1 *= par7;
        par3 *= par7;
        par5 *= par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }
}
