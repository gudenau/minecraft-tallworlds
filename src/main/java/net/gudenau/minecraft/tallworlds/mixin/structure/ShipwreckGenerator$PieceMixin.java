package net.gudenau.minecraft.tallworlds.mixin.structure;

import net.minecraft.structure.ShipwreckGenerator;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructurePieceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ShipwreckGenerator.Piece.class)
public abstract class ShipwreckGenerator$PieceMixin extends SimpleStructurePiece{
    private ShipwreckGenerator$PieceMixin(StructurePieceType structurePieceType, int i){
        super(structurePieceType, i);
    }
    
    @ModifyConstant(
        method = "generate",
        constant = @Constant(intValue = 256)
    )
    private static int generate(int original){
        return 512;
    }
}
