package com.zing.zinglib.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TableBlock extends Block {
    // Composes a flat tabletop surface and 4 support pillars
    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0), // Table Top surface layer
        Block.box(0.0, 0.0, 0.0, 2.0, 14.0, 2.0),     // Leg North-West
        Block.box(14.0, 0.0, 0.0, 16.0, 14.0, 2.0),   // Leg North-East
        Block.box(0.0, 0.0, 14.0, 2.0, 14.0, 16.0),   // Leg South-West
        Block.box(14.0, 0.0, 14.0, 16.0, 14.0, 16.0)  // Leg South-East
    );

    public TableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}