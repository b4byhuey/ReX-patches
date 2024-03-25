package app.revanced.patches.music.utils.intenthook

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patches.music.utils.integrations.Constants.INTEGRATIONS_PATH
import app.revanced.patches.music.utils.intenthook.fingerprints.GoogleApiActivityFingerprint
import app.revanced.patches.music.utils.settings.SettingsPatch
import app.revanced.util.exception

@Patch(dependencies = [SettingsPatch::class])
object IntentHookPatch : BytecodePatch(
    setOf(GoogleApiActivityFingerprint)
) {
    override fun execute(context: BytecodeContext) {
        GoogleApiActivityFingerprint.result?.let {
            it.mutableMethod.apply {
                addInstruction(
                    1,
                    "invoke-static {p0}, $INTEGRATIONS_PATH/settingsmenu/ReVancedSettingActivity;->initializeSettings(Landroid/app/Activity;)V"
                )
            }
        } ?: throw GoogleApiActivityFingerprint.exception
    }
}