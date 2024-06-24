package app.revanced.patches.music.misc.backgroundplay.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction35c

object BackgroundPlaybackParentFingerprint : MethodFingerprint(
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.PUBLIC,
    parameters = listOf("L"),
    opcodes = listOf(
        Opcode.INVOKE_STATIC, // target method
        Opcode.MOVE_RESULT,
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT_OBJECT
    ),
    customFingerprint = custom@{ methodDef, _ ->
        if (methodDef.name != "handleVideoStageEvent")
            return@custom false

        val instructions = methodDef.implementation?.instructions!!

        for (instruction in instructions) {
            if (instruction.opcode != Opcode.INVOKE_STATIC)
                continue

            val invokeInstruction = instruction as Instruction35c

            if (invokeInstruction?.reference.toString() == "Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;")
                return@custom true
        }
        return@custom false
    }
)