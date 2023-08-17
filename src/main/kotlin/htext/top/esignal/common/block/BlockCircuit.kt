package htext.top.esignal.common.block

import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import com.google.common.collect.UnmodifiableIterator
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.RedstoneWireBlock
import net.minecraft.block.ShapeContext
import net.minecraft.block.enums.WireConnection
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.state.property.Property
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.*

class BlockCircuit(settings: Settings?) : Block(settings) {
	private var shapesCache: MutableMap<BlockState, VoxelShape> = Maps.newHashMap()

	companion object {
		val DOT_SHAPE: VoxelShape = createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0)
		val CHARGE: BooleanProperty = BooleanProperty.of("charged")
		val CONNECTION_EAST: EnumProperty<WireConnection> = Properties.EAST_WIRE_CONNECTION
		val CONNECTION_WEST: EnumProperty<WireConnection> = Properties.WEST_WIRE_CONNECTION
		val CONNECTION_NORTH: EnumProperty<WireConnection> = Properties.NORTH_WIRE_CONNECTION
		val CONNECTION_SOUTH: EnumProperty<WireConnection> = Properties.SOUTH_WIRE_CONNECTION

		val DIRECTION_TO_WIRE_CONNECTION_PROPERTY: Map<Direction, EnumProperty<WireConnection>> = Maps.newEnumMap(
			ImmutableMap.of(
				Direction.NORTH, CONNECTION_NORTH,
				Direction.EAST, CONNECTION_EAST,
				Direction.SOUTH, CONNECTION_SOUTH,
				Direction.WEST, CONNECTION_WEST
			)
		)

		private val shapeFloor: EnumMap<Direction, VoxelShape> = Maps.newEnumMap(
			ImmutableMap.of
				(Direction.NORTH, createCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
				Direction.SOUTH, createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
				Direction.EAST, createCuboidShape(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
				Direction.WEST, createCuboidShape(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)))
		private val shapeUp: EnumMap<Direction, VoxelShape> = Maps.newEnumMap(
			ImmutableMap.of(
				Direction.NORTH, VoxelShapes.union(
					shapeFloor[Direction.NORTH], createCuboidShape(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)
				), Direction.SOUTH, VoxelShapes.union(
					shapeFloor[Direction.SOUTH], createCuboidShape(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)
				), Direction.EAST, VoxelShapes.union(
					shapeFloor[Direction.EAST], createCuboidShape(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)
				), Direction.WEST, VoxelShapes.union(
					shapeFloor[Direction.WEST], createCuboidShape(0.0, 0.0, 3.0, 1.0, 16.0, 13.0)
				)))
	}

	init {
		defaultState = defaultState
			.with(CHARGE, false)
			.with(CONNECTION_EAST, WireConnection.NONE)
			.with(CONNECTION_WEST, WireConnection.NONE)
			.with(CONNECTION_NORTH, WireConnection.NONE)
			.with(CONNECTION_SOUTH, WireConnection.NONE)

		val iterator: UnmodifiableIterator<*> = getStateManager().states.iterator()
		while (iterator.hasNext()){
			val blockState = iterator.next() as BlockState
			if (blockState.get(CHARGE) == true){
				shapesCache[blockState] = getShapeForState(blockState)
			}
		}
	}

	private fun getShapeForState(blockState: BlockState): VoxelShape {
		var voxelShape = DOT_SHAPE
		val iterator: Iterator<*> = Direction.Type.HORIZONTAL.iterator()

		while (iterator.hasNext()) {
			val direction = iterator.next() as Direction
			val connection: WireConnection = blockState.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY[direction])

			if (connection == WireConnection.SIDE) voxelShape = VoxelShapes.union(voxelShape, shapeFloor[direction])
			if(connection == WireConnection.UP) voxelShape = VoxelShapes.union(voxelShape, shapeUp[direction])
		}
		return voxelShape
	}

	override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
		builder
			.add(CHARGE)
			.add(CONNECTION_EAST)
			.add(CONNECTION_WEST)
			.add(CONNECTION_NORTH)
			.add(CONNECTION_SOUTH)
	}
	@Deprecated("Deprecated in Java", ReplaceWith("DOT_SHAPE", "htext.top.esignal.common.block.BlockCircuit.Companion.DOT_SHAPE"))
	override fun getOutlineShape (state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
		return DOT_SHAPE
	}
	@Deprecated("Deprecated in Java", ReplaceWith("VoxelShapes.empty()", "net.minecraft.util.shape.VoxelShapes"))
	override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
		return VoxelShapes.empty()
	}

	@Deprecated("Deprecated in Java")
	override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
		val blockPos = pos.down(1)
		val blockState = world.getBlockState(blockPos)
		return (! blockState.isAir && blockState.isFullCube(world, pos))
	}

	@Deprecated("Deprecated in Java")
	override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos?, block: Block?, fromPos: BlockPos?, notify: Boolean) {
		if (world.isClient) { return }
		if (state.canPlaceAt(world, pos)) {
			this.update(world, pos, state)
			return
		}
		dropStacks(state, world, pos)
		world.removeBlock(pos, false)
	}

//	@Deprecated("Deprecated in Java")
//	override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState?, world: WorldAccess?, pos: BlockPos?, neighborPos: BlockPos?): BlockState? {
//
//	}

	private fun update(world: World, pos: BlockPos?, state: BlockState) {
		getShapeForState(state)
	}
}
