package top.htext.esignal.common.block.interfaces

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess

interface SignalChargeableBlock {
	fun isEmittedSignal(pos: BlockPos, state: BlockState, access: WorldAccess): Boolean
	fun isReceivedSignal(pos: BlockPos, state: BlockState, direction: Direction, access: WorldAccess): Boolean
}