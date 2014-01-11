package tachyony.nullPower.entity

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import net.minecraft.util.DamageSource

class EntityRifleBolt(par1World: World, par2EntityLiving: EntityLivingBase) extends EntityThrowable(par1World, par2EntityLiving) {
   override protected def onImpact(par1MovingObjectPosition: MovingObjectPosition)
   {
       if (par1MovingObjectPosition.entityHit != null)
        {
            var b0: Byte = 12;
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), b0.asInstanceOf[Float]);
        }

        for (i <- 0 until 8)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
   }
}
