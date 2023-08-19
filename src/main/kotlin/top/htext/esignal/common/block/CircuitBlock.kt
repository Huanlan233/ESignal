package top.htext.esignal.common.block

import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.enums.WireConnection
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockPos.Mutable
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import top.htext.esignal.ESignal


class CircuitBlock(settings: Settings?) : Block(settings), SidedBlock {
	private val shapeCache: MutableMap<BlockState, VoxelShape> = Maps.newHashMap()
	private val dotState: BlockState
	private val charge = false
	private fun getShapeForState(state: BlockState): VoxelShape {
		var voxelShape: VoxelShape = DOT_SHAPE

		for (direction in Direction.Type.HORIZONTAL) {
			val wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY[direction])
			if (wireConnection != WireConnection.UP) continue
			voxelShape = VoxelShapes.union(voxelShape, SHAPES_UP[direction])
		}
		return voxelShape
	}


	init {
		defaultState = stateManager.defaultState
			.with(WIRE_CONNECTION_NORTH, WireConnection.NONE)
			.with(WIRE_CONNECTION_EAST, WireConnection.NONE)
			.with(WIRE_CONNECTION_SOUTH, WireConnection.NONE)
			.with(WIRE_CONNECTION_WEST, WireConnection.NONE)
			.with(CHARGE, false)
		dotState = defaultState
			.with(WIRE_CONNECTION_NORTH, WireConnection.SIDE)
			.with(WIRE_CONNECTION_EAST, WireConnection.SIDE)
			.with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE)
			.with(WIRE_CONNECTION_WEST, WireConnection.SIDE)

		for (blockState in getStateManager().states) {
			if (blockState.get(CHARGE)) continue
			shapeCache[blockState] = getShapeForState(blockState)
		}
	}

	companion object {
		val WIRE_CONNECTION_NORTH: EnumProperty<WireConnection> = Properties.NORTH_WIRE_CONNECTION
		val WIRE_CONNECTION_EAST: EnumProperty<WireConnection> = Properties.EAST_WIRE_CONNECTION
		val WIRE_CONNECTION_SOUTH: EnumProperty<WireConnection> = Properties.SOUTH_WIRE_CONNECTION
		val WIRE_CONNECTION_WEST: EnumProperty<WireConnection> = Properties.WEST_WIRE_CONNECTION
		val CHARGE: BooleanProperty = BooleanProperty.of("charge")
		val DIRECTION_TO_WIRE_CONNECTION_PROPERTY: Map<Direction, EnumProperty<WireConnection>> = Maps.newEnumMap(
			ImmutableMap.of(
				Direction.NORTH, WIRE_CONNECTION_NORTH,
				Direction.EAST, WIRE_CONNECTION_EAST,
				Direction.SOUTH, WIRE_CONNECTION_SOUTH,
				Direction.WEST, WIRE_CONNECTION_WEST
			)
		)
		private val DOT_SHAPE = createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0)
		private val SHAPES_FLOOR: Map<Direction, VoxelShape> = Maps.newEnumMap(
			ImmutableMap.of(
				Direction.NORTH, createCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
				Direction.SOUTH, createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
				Direction.EAST, createCuboidShape(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
				Direction.WEST, createCuboidShape(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)
			)
		)
		private val SHAPES_UP: Map<Direction, VoxelShape> = Maps.newEnumMap(
			ImmutableMap.of(
				Direction.NORTH, VoxelShapes.union(
					SHAPES_FLOOR[Direction.NORTH], createCuboidShape(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)), Direction.SOUTH, VoxelShapes.union(
					SHAPES_FLOOR[Direction.SOUTH], createCuboidShape(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)), Direction.EAST, VoxelShapes.union(
					SHAPES_FLOOR[Direction.EAST], createCuboidShape(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)), Direction.WEST, VoxelShapes.union(
					SHAPES_FLOOR[Direction.WEST], createCuboidShape(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))
			)
		)
		private fun isFullyConnected(state: BlockState): Boolean {
			return state.get(WIRE_CONNECTION_NORTH).isConnected &&
					state.get(WIRE_CONNECTION_SOUTH).isConnected &&
					state.get(WIRE_CONNECTION_EAST).isConnected &&
					state.get(WIRE_CONNECTION_WEST).isConnected
		}

		private fun isNotConnected(state: BlockState): Boolean {
			return ! state.get(WIRE_CONNECTION_NORTH).isConnected &&
					! state.get(WIRE_CONNECTION_SOUTH).isConnected &&
					! state.get(WIRE_CONNECTION_EAST).isConnected &&
					! state.get(WIRE_CONNECTION_WEST).isConnected
		}
	}

	override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
		builder.add(
			WIRE_CONNECTION_NORTH,
			WIRE_CONNECTION_EAST,
			WIRE_CONNECTION_SOUTH,
			WIRE_CONNECTION_WEST,
			CHARGE
		)
	}

	@Deprecated("Deprecated in Java", ReplaceWith("VoxelShapes.empty()", "net.minecraft.util.shape.VoxelShapes"))
	override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
		return VoxelShapes.empty()
	}

	@Deprecated("Deprecated in Java")
	override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
		return shapeCache[state.with(CHARGE, false)] !!
	}
	@Deprecated("Deprecated in Java", ReplaceWith("super.prepare(state, world, pos, flags, maxUpdateDepth)", "net.minecraft.block.Block"))
	override fun prepare(state: BlockState, access: WorldAccess, pos: BlockPos, flags: Int, maxUpdateDepth: Int) {
		val mutable = Mutable()

		for ( direction : Direction in Direction.Type.HORIZONTAL ) { // Traverses changes in four direction.

			mutable.set(pos, direction).offset(direction)
			val mutableState = access.getBlockState(mutable)

			if (mutableState.isOf(this)) { // Updates self.
				var newState = getStateForNeighborUpdate(state, direction, mutableState, access, pos, mutable)

				if ( direction == Direction.EAST || direction == Direction.WEST ) newState = state.with(WIRE_CONNECTION_WEST, WireConnection.SIDE).with(WIRE_CONNECTION_EAST, WireConnection.SIDE)
				if ( direction == Direction.SOUTH || direction == Direction.NORTH ) newState = state.with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE).with(WIRE_CONNECTION_NORTH, WireConnection.SIDE)

				replace(state, newState, access, pos, flags)
			}
		}
	}

	@Deprecated("Deprecated in Java")
	override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
		if (world.isClient) return
		ESignal.LOGGER.info("Updated: BlockPos: {${pos.x}, ${pos.y}, ${pos.z}}")
	}

	private fun update(world: WorldAccess, pos: BlockPos, state: BlockState){

	}
}
