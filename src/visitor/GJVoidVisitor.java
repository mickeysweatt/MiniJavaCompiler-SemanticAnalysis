//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * All GJ void visitors must implement this interface.
 */

public interface GJVoidVisitor<A> {

   //
   // GJ void Auto class visitors
   //

   public void visit(NodeList n, A argu);
   public void visit(NodeListOptional n, A argu);
   public void visit(NodeOptional n, A argu);
   public void visit(NodeSequence n, A argu);
   public void visit(NodeToken n, A argu);

   //
   // User-generated visitor methods below
   //

   /**
    * mainClass -> MainClass()
    * nodeListOptional -> ( TypeDeclaration() )*
    * nodeToken -> <EOF>
    */
   public void visit(Goal n, A argu);

   /**
    * nodeToken -> "class"
    * identifier -> Identifier()
    * nodeToken1 -> "{"
    * nodeToken2 -> "public"
    * nodeToken3 -> "static"
    * nodeToken4 -> "void"
    * nodeToken5 -> "main"
    * nodeToken6 -> "("
    * nodeToken7 -> "String"
    * nodeToken8 -> "["
    * nodeToken9 -> "]"
    * identifier1 -> Identifier()
    * nodeToken10 -> ")"
    * nodeToken11 -> "{"
    * nodeListOptional -> ( VarDeclaration() )*
    * nodeListOptional1 -> ( Statement() )*
    * nodeToken12 -> "}"
    * nodeToken13 -> "}"
    */
   public void visit(MainClass n, A argu);

   /**
    * nodeChoice -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public void visit(TypeDeclaration n, A argu);

   /**
    * nodeToken -> "class"
    * identifier -> Identifier()
    * nodeToken1 -> "{"
    * nodeListOptional -> ( VarDeclaration() )*
    * nodeListOptional1 -> ( MethodDeclaration() )*
    * nodeToken2 -> "}"
    */
   public void visit(ClassDeclaration n, A argu);

   /**
    * nodeToken -> "class"
    * identifier -> Identifier()
    * nodeToken1 -> "extends"
    * identifier1 -> Identifier()
    * nodeToken2 -> "{"
    * nodeListOptional -> ( VarDeclaration() )*
    * nodeListOptional1 -> ( MethodDeclaration() )*
    * nodeToken3 -> "}"
    */
   public void visit(ClassExtendsDeclaration n, A argu);

   /**
    * type -> Type()
    * identifier -> Identifier()
    * nodeToken -> ";"
    */
   public void visit(VarDeclaration n, A argu);

   /**
    * nodeToken -> "public"
    * type -> Type()
    * identifier -> Identifier()
    * nodeToken1 -> "("
    * nodeOptional -> ( FormalParameterList() )?
    * nodeToken2 -> ")"
    * nodeToken3 -> "{"
    * nodeListOptional -> ( VarDeclaration() )*
    * nodeListOptional1 -> ( Statement() )*
    * nodeToken4 -> "return"
    * expression -> Expression()
    * nodeToken5 -> ";"
    * nodeToken6 -> "}"
    */
   public void visit(MethodDeclaration n, A argu);

   /**
    * formalParameter -> FormalParameter()
    * nodeListOptional -> ( FormalParameterRest() )*
    */
   public void visit(FormalParameterList n, A argu);

   /**
    * type -> Type()
    * identifier -> Identifier()
    */
   public void visit(FormalParameter n, A argu);

   /**
    * nodeToken -> ","
    * formalParameter -> FormalParameter()
    */
   public void visit(FormalParameterRest n, A argu);

   /**
    * nodeChoice -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public void visit(Type n, A argu);

   /**
    * nodeToken -> "int"
    * nodeToken1 -> "["
    * nodeToken2 -> "]"
    */
   public void visit(ArrayType n, A argu);

   /**
    * nodeToken -> "boolean"
    */
   public void visit(BooleanType n, A argu);

   /**
    * nodeToken -> "int"
    */
   public void visit(IntegerType n, A argu);

   /**
    * nodeChoice -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public void visit(Statement n, A argu);

   /**
    * nodeToken -> "{"
    * nodeListOptional -> ( Statement() )*
    * nodeToken1 -> "}"
    */
   public void visit(Block n, A argu);

   /**
    * identifier -> Identifier()
    * nodeToken -> "="
    * expression -> Expression()
    * nodeToken1 -> ";"
    */
   public void visit(AssignmentStatement n, A argu);

   /**
    * identifier -> Identifier()
    * nodeToken -> "["
    * expression -> Expression()
    * nodeToken1 -> "]"
    * nodeToken2 -> "="
    * expression1 -> Expression()
    * nodeToken3 -> ";"
    */
   public void visit(ArrayAssignmentStatement n, A argu);

   /**
    * nodeToken -> "if"
    * nodeToken1 -> "("
    * expression -> Expression()
    * nodeToken2 -> ")"
    * statement -> Statement()
    * nodeToken3 -> "else"
    * statement1 -> Statement()
    */
   public void visit(IfStatement n, A argu);

   /**
    * nodeToken -> "while"
    * nodeToken1 -> "("
    * expression -> Expression()
    * nodeToken2 -> ")"
    * statement -> Statement()
    */
   public void visit(WhileStatement n, A argu);

   /**
    * nodeToken -> "System.out.println"
    * nodeToken1 -> "("
    * expression -> Expression()
    * nodeToken2 -> ")"
    * nodeToken3 -> ";"
    */
   public void visit(PrintStatement n, A argu);

   /**
    * nodeChoice -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public void visit(Expression n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "&&"
    * primaryExpression1 -> PrimaryExpression()
    */
   public void visit(AndExpression n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "<"
    * primaryExpression1 -> PrimaryExpression()
    */
   public void visit(CompareExpression n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "+"
    * primaryExpression1 -> PrimaryExpression()
    */
   public void visit(PlusExpression n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "-"
    * primaryExpression1 -> PrimaryExpression()
    */
   public void visit(MinusExpression n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "*"
    * primaryExpression1 -> PrimaryExpression()
    */
   public void visit(TimesExpression n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "["
    * primaryExpression1 -> PrimaryExpression()
    * nodeToken1 -> "]"
    */
   public void visit(ArrayLookup n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "."
    * nodeToken1 -> "length"
    */
   public void visit(ArrayLength n, A argu);

   /**
    * primaryExpression -> PrimaryExpression()
    * nodeToken -> "."
    * identifier -> Identifier()
    * nodeToken1 -> "("
    * nodeOptional -> ( ExpressionList() )?
    * nodeToken2 -> ")"
    */
   public void visit(MessageSend n, A argu);

   /**
    * expression -> Expression()
    * nodeListOptional -> ( ExpressionRest() )*
    */
   public void visit(ExpressionList n, A argu);

   /**
    * nodeToken -> ","
    * expression -> Expression()
    */
   public void visit(ExpressionRest n, A argu);

   /**
    * nodeChoice -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public void visit(PrimaryExpression n, A argu);

   /**
    * nodeToken -> <INTEGER_LITERAL>
    */
   public void visit(IntegerLiteral n, A argu);

   /**
    * nodeToken -> "true"
    */
   public void visit(TrueLiteral n, A argu);

   /**
    * nodeToken -> "false"
    */
   public void visit(FalseLiteral n, A argu);

   /**
    * nodeToken -> <IDENTIFIER>
    */
   public void visit(Identifier n, A argu);

   /**
    * nodeToken -> "this"
    */
   public void visit(ThisExpression n, A argu);

   /**
    * nodeToken -> "new"
    * nodeToken1 -> "int"
    * nodeToken2 -> "["
    * expression -> Expression()
    * nodeToken3 -> "]"
    */
   public void visit(ArrayAllocationExpression n, A argu);

   /**
    * nodeToken -> "new"
    * identifier -> Identifier()
    * nodeToken1 -> "("
    * nodeToken2 -> ")"
    */
   public void visit(AllocationExpression n, A argu);

   /**
    * nodeToken -> "!"
    * expression -> Expression()
    */
   public void visit(NotExpression n, A argu);

   /**
    * nodeToken -> "("
    * expression -> Expression()
    * nodeToken1 -> ")"
    */
   public void visit(BracketExpression n, A argu);

}

