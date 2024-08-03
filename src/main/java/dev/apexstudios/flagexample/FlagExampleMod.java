package dev.apexstudios.flagexample;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.flag.Flag;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(FlagExampleMod.ID)
public final class FlagExampleMod {
    public static final String ID = "flag_example_mod";

    // define out custom `experimental` flag
    // note: this does not register the flag, just defines the identifier used for the flag
    public static final Flag FLAG_EXPERIMENTAL = Flag.of(ID, "experimental");

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ID);

    // register new item which is locked behind our `experimental` flag
    public static final DeferredItem<Item> FLAGGED_ITEM = ITEMS.registerSimpleItem("flagged_item", new Item.Properties().requiredFlags(FLAG_EXPERIMENTAL));

    // register new block which is locked behind out `experimental` flag
    public static final DeferredBlock<Block> FLAGGED_BLOCK = BLOCKS.registerSimpleBlock("flagged_block", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiredFlags(FLAG_EXPERIMENTAL));
    // this item will automatically be locked behind our `experimental` flag
    // due to it being paired with `FLAGGED_BLOCK`
    public static final DeferredItem<BlockItem> FLAGGED_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(FLAGGED_BLOCK);

    public FlagExampleMod(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
    }
}
