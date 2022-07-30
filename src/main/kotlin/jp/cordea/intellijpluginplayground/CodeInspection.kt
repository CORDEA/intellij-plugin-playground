package jp.cordea.intellijpluginplayground

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.debugger.sequence.psi.callName
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtPsiFactory

class CodeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = Visitor(holder)
}

private class Visitor(private val holder: ProblemsHolder) : PsiElementVisitor() {
    override fun visitElement(element: PsiElement) {
        super.visitElement(element)
        if (element !is KtDotQualifiedExpression || element.receiverExpression.text != "Log") {
            return
        }
        holder.registerProblem(element, "Avoid using Log, consider using Timber instead.", QuickFix())
    }
}

private class QuickFix : LocalQuickFix {
    override fun getFamilyName(): String = "Replace with Timber"

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val element = descriptor.psiElement as KtDotQualifiedExpression
        val expression = (element.selectorExpression as? KtCallExpression) ?: return
        val level = expression.callName()
        val args = expression.valueArguments.map { it.text }
        element.replace(
                KtPsiFactory(project)
                        .createExpression("Timber.${level}(${args[0]}, ${args[1]})")
        )
    }
}
