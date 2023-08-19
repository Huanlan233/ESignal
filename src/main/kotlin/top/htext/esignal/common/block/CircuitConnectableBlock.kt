package top.htext.esignal.common.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess

/**
 * Makes the block can connect with circuits.
 * */
interface CircuitConnectableBlock {
	/**
	* Updates self and adjacent blocks' connection states.
	 * @param pos The position of the block what will be updated.
	 * @param state The states of the block what will be updated.
	*/
	fun updateConnection(pos: BlockPos, state: BlockState, access: WorldAccess)
}