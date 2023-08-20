package top.htext.esignal.common.block.interfaces

import net.minecraft.block.AbstractBlock
import net.minecraft.block.BlockState
import net.minecraft.block.enums.WireConnection
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess

/**
 * Makes the block can connect with circuits.
 * */
interface SignalConnectableBlock {
	/**
	* Updates self and adjacent blocks' connections.
	 * @param pos The position of the block in direction what will be updated.
	 * @param state The states of the block in direction what will be updated.
	 * @param direction The side what will be updated.
	 * @return The connection states.
	*/
	fun updateConnection(pos: BlockPos, state: BlockState, direction: Direction,access: WorldAccess): WireConnection
}