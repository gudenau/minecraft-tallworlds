package net.gudenau.minecraft.tallworlds.mixin.util.crash;

import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin{
    @Shadow @Final private CrashReportSection systemDetailsSection;
    
    @Inject(
        method = "fillSystemDetails",
        at = @At("HEAD")
    )
    private void fillSystemDetails(CallbackInfo callbackInfo){
        systemDetailsSection.add("Tall Worlds Disclaimer", "This instance has TallWorlds present, this is a highly invasive mod and crashes are expected (especially when combined with other mods).");
    }
}
