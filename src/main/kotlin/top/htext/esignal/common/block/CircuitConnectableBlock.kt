package top.htext.esignal.common.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess

interface CircuitConnectableBlock {
	/**
	* Updates self or adjacent blocks.
	*/
	fun updateConnection(pos: BlockPos, state: BlockState, access: WorldAccess)
	fun getSideConnectable(pos: BlockPos, direction: Direction): BlockPos
}