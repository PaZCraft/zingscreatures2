package com.zing.zinglib.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class VaseBlock extends Block {
    // Combine bounding boxes to fit a stylized vase shape perfectly
    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(5.0, 0.0, 5.0, 11.0, 2.0, 11.0),   // Base footing
        Block.box(4.0, 2.0, 4.0, 12.0, 10.0, 12.0),  // Main wide body
        Block.box(6.0, 10.0, 6.0, 10.0, 13.0, 10.0), // Narrow neck
        Block.box(5.0, 13.0, 5.0, 11.0, 14.0, 11.0)  // Flared rim
    );

    public VaseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE; // Overrides the selection and physical collision lines
    }
}