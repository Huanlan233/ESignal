package top.htext.esignal.utils

import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess
import top.htext.esignal.common.block.interfaces.SignalChargeableBlock
import top.htext.esignal.common.block.interfaces.SignalConnectableBlock

object SignalUtils {
	fun isConnectable(pos: BlockPos, access: WorldAccess): Boolean{
		return access.getBlockState(pos).block is SignalConnectableBlock
	}

	fun isChargeable(pos: BlockPos, access: WorldAccess): Boolean{
		return access.getBlockState(pos).block is SignalChargeableBlock
	}
}