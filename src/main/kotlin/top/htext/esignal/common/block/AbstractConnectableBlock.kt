package top.htext.esignal.common.block

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.enums.WireConnection
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

abstract class AbstractConnectableBlock(settings: Settings?) : Block(settings) {
	override fun prepare(state: BlockState, access: WorldAccess, pos: BlockPos, flags: Int, maxUpdateDepth: Int) {
		for (direction: Direction in Direction.Type.HORIZONTAL) { // Traverses changes in six directions.
			val updateConnection = updateConnection(pos, state, direction, access)
			val newState = state
				.with(CircuitBlock.DIRECTION_TO_WIRE_CONNECTION_PROPERTY[direction], updateConnection)
			Block.replace(state, newState, access, pos, flags)
		}
	}
	override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
		if (world.isClient) return
		if (!canPlaceAt(state, world, pos)){
			dropStacks(state, world, pos)
			world.removeBlock(pos, false)
		}
		for (direction: Direction in Direction.Type.HORIZONTAL) { // Traverses changes in six directions.
			val updateConnection = updateConnection(pos, state, direction, world)
			val newState = state
				.with(CircuitBlock.DIRECTION_TO_WIRE_CONNECTION_PROPERTY[direction], updateConnection)
			replace(state, newState, world, pos, 2)
		}
	}
	abstract fun updateConnection(pos: BlockPos, state: BlockState, direction: Direction, access: WorldAccess): WireConnection
}