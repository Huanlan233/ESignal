package top.htext.esignal.utils

import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess
import top.htext.esignal.common.block.CircuitChargeableBlock
import top.htext.esignal.common.block.CircuitConnectableBlock

object SignalUtils {
	fun isConnectable(pos: BlockPos, access: WorldAccess): Boolean{
		return access.getBlockState(pos).block is CircuitConnectableBlock
	}

	fun isChargeable(pos: BlockPos, access: WorldAccess): Boolean{
		return access.getBlockState(pos).block is CircuitChargeableBlock
	}
}