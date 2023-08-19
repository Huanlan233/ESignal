package top.htext.esignal.common.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess

interface CircuitChargeableBlock {
	fun emitSignal(pos: BlockPos, state: BlockState, access: WorldAccess): Boolean
	fun receiveSignal(pos: BlockPos, state: BlockState, access: WorldAccess)
}