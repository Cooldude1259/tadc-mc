package com.cooldude12.unnoficialtadc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class CostumeBlockEntity extends BlockEntity {

    private ItemStack costume = ItemStack.EMPTY;
    private Block originalBlock = Blocks.AIR;

    public CostumeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COSTUME_BLOCK_ENTITY, pos, state);
    }

    public ItemStack getCostume() {
        return costume;
    }

    public void setCostume(ItemStack costume) {
        this.costume = costume;
        setChanged(); // tells the game "this block entity's data changed, please save/sync it"
    }

    public Block getOriginalBlock() {
        return originalBlock;
    }

    public void setOriginalBlock(Block block) {
        this.originalBlock = block;
        setChanged();
    }

    // ---- Disk persistence (world save/reload) ----

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("costume", ItemStack.CODEC, this.costume);
        Identifier originalId = BuiltInRegistries.BLOCK.getKey(this.originalBlock);
        output.putString("original_block", originalId.toString());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.costume = input.read("costume", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        String originalId = input.getStringOr("original_block", "minecraft:air");
        this.originalBlock = BuiltInRegistries.BLOCK.get(Identifier.parse(originalId))
                .map(net.minecraft.core.Holder.Reference::value)
                .orElse(Blocks.AIR);
    }

    // ---- Client sync (so nearby players see live changes, not just on chunk reload) ----

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }
}
