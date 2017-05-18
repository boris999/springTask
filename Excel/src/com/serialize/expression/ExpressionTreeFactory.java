package com.serialize.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import com.model.expression.BinaryOperator;
import com.model.expression.BinaryOperatorNode;
import com.model.expression.Condition;
import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceNode;
import com.model.expression.ValueNode;

public final class ExpressionTreeFactory {
	private static Pattern letterPattern = Pattern.compile("([\\w&&[^\\d_]])");
	private Pattern operatorPattern = Pattern.compile("[+-^*//]");
	private Pattern digitPattern = Pattern.compile("[0-9.]");

	private ExpressionTreeFactory() {
	}

	public static ExpressionTreeNode parseExpression(String expression) {
		Stack<ExpressionTreeNode> stack = new Stack<>();
		Stack<BinaryOperator> operatorStack = new Stack<>();
		return parseExpressionAndNodeStack(expression, stack, operatorStack);

	}

	public static ExpressionTreeNode parseExpressionAndNodeStack(String expression, Stack<ExpressionTreeNode> stack,
			Stack<BinaryOperator> operatorStack) {
		char[] array = expression.toCharArray();
		StringBuilder sb = new StringBuilder("");
		Condition hasLetter = Condition.NOT_EVALUATED;
		Condition hasDigit = Condition.NOT_EVALUATED;
		boolean missingRight = false;
		boolean startingOperatorConsumed = false;
		for (char c : array) {
			if (c == ' ') {
				continue;
			}
			hasLetter = hasLetter(c, hasLetter);
			hasDigit = hasDigit(c, hasDigit);
			// TODO to add ( to the check for operator and another else if when there is )
			if (isOperator(c)) {
				if (!startingOperatorConsumed && stack.isEmpty() && sb.toString().equals("")) {
					sb.append("0");
					startingOperatorConsumed = true;
					hasDigit = Condition.TRUE;
				}
				boolean isPriorityOperator = isPriorityOperator(c);
				int operatorstackSize = operatorStack.size();
				if (missingRight) {// no right node
					ExpressionTreeNode previous = stack.pop();
					sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
					hasLetter = Condition.NOT_EVALUATED;
					hasDigit = Condition.NOT_EVALUATED;
					ExpressionTreeNode last = stack.pop();
					((BinaryOperatorNode) previous).setRigth(last);
					stack.push(previous);
					operatorStack.push(getOperator(c));
					missingRight = false;
				} else if (!isPriorityOperator && (operatorstackSize == 0)) {
					sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
					hasLetter = Condition.NOT_EVALUATED;
					hasDigit = Condition.NOT_EVALUATED;
					operatorStack.push(getOperator(c));
				} else if (!isPriorityOperator && (operatorstackSize == 1)) {
					sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
					hasLetter = Condition.NOT_EVALUATED;
					hasDigit = Condition.NOT_EVALUATED;
					ExpressionTreeNode right = stack.pop();
					ExpressionTreeNode left = stack.pop();
					BinaryOperator previousOperator = operatorStack.pop();
					BinaryOperatorNode node = new BinaryOperatorNode(left, right, previousOperator);
					stack.push(node);
					operatorStack.push(getOperator(c));

				} else if (isPriorityOperator) {
					sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
					hasLetter = Condition.NOT_EVALUATED;
					hasDigit = Condition.NOT_EVALUATED;
					ExpressionTreeNode left = stack.pop();
					BinaryOperator operator = getOperator(c);
					BinaryOperatorNode node = new BinaryOperatorNode(left, null, operator);
					stack.push(node);
					missingRight = true;
				}
			} else {
				sb.append(c);
				startingOperatorConsumed = true;
			}
		}
		if (!sb.toString().equals("")) {
			sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
			hasLetter = Condition.NOT_EVALUATED;
			hasDigit = Condition.NOT_EVALUATED;
		}
		while (stack.size() > 1) {
			ExpressionTreeNode last = stack.pop();
			ExpressionTreeNode beforelast = stack.pop();
			if ((beforelast instanceof BinaryOperatorNode) && !beforelast.hasRight()) {
				((BinaryOperatorNode) beforelast).setRigth(last);
				stack.push(beforelast);
			} else {
				BinaryOperator lastOperator = operatorStack.pop();
				BinaryOperatorNode node = new BinaryOperatorNode(beforelast, last, lastOperator);
				stack.push(node);
			}
		}

		return stack.pop();
	}

	private static StringBuilder putReferenceOrTreeNodeInStack(Stack<ExpressionTreeNode> stack, StringBuilder sb, Condition hasLetter,
			Condition hasDigit) {
		if (hasLetter.equals(Condition.TRUE) && hasDigit.equals(Condition.TRUE)) {
			stack.push(new ReferenceNode(sb.toString()));
		} else if (hasLetter.equals(Condition.FALSE) && hasDigit.equals(Condition.TRUE)) {
			stack.push(new ValueNode(Double.parseDouble(sb.toString())));
		}
		return new StringBuilder("");

	}

	private static boolean isOperator(char c) {
		if ((c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '^') || (c == '(') || (c == ')')) {
			return true;
		}
		return false;
	}

	private static boolean isPriorityOperator(char c) {
		return (c == '*') || (c == '/') || (c == '^');
	}

	private static boolean isMinus(char c) {
		return (c == '-');
	}

	private static boolean isOpeningBracket(char c) {
		if ((c == '(')) {
			return true;
		}
		return false;
	}

	private static boolean isClosingBracket(char c) {
		if ((c == ')')) {
			return true;
		}
		return false;
	}

	private static boolean isBracket(char c) {
		if ((c == '(') || (c == ')')) {
			return true;
		}
		return false;
	}

	private static Condition hasDigit(char c, Condition condition) {
		if (condition.equals(Condition.TRUE)) {
			return Condition.TRUE;
		}
		if ((c == '0') || (c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') || (c == '6') || (c == '7') || (c == '8') || (c == '9')
				|| (c == '.')) {
			return Condition.TRUE;
		}
		return Condition.FALSE;
	}

	private static boolean isNoNode(char c) {
		if ((c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '^') || (c == '(') || (c == ')')) {
			return true;
		}
		return false;
	}

	private static Condition hasLetter(char c, Condition condition) {
		if (condition.equals(Condition.TRUE)) {
			return Condition.TRUE;
		}
		char symbol = Character.toLowerCase(c);
		if ((symbol >= 'a') && (symbol <= 'z')) {
			return Condition.TRUE;
		}
		return Condition.FALSE;
	}

	private static BinaryOperator getOperator(char c) {
		switch (c) {
		case '+':
			return BinaryOperator.PLUS;
		case '-':
			return BinaryOperator.MINUS;
		case '*':
			return BinaryOperator.MULTIPLY;
		case '/':
			return BinaryOperator.DIVIDE;
		case '^':
			return BinaryOperator.POWER;
		default:
			return BinaryOperator.BRACKET;
		}
	}

	private static void addIfNotEmpty(List<String> list, String value) {
		if (!value.equals("")) {
			list.add(value);
		}
	}

	public static List<String> removeOuterBrackets(String expression) {
		StringBuilder beforeBrackets = new StringBuilder("");
		StringBuilder afterBrackets = new StringBuilder("");
		StringBuilder newExpression = new StringBuilder("");
		int bracketsFound = 0;
		int depth = countBrackesDepth(expression);
		char[] array = expression.toCharArray();
		boolean closingFound = false;
		boolean openingFound = false;
		boolean maxDepthFound = true;
		for (char c : array) {
			if (closingFound) {
				afterBrackets.append(c);
			}
			if (isClosingBracket(c)) {
				bracketsFound--;
				if (bracketsFound == 0) {
					closingFound = true;
				}
				maxDepthFound = false;
			}
			if (openingFound && !closingFound) {
				newExpression.append(c);
			}
			if (isOpeningBracket(c)) {
				bracketsFound++;
				openingFound = true;
				if (bracketsFound == depth) {
					maxDepthFound = true;
				}
			}
			if ((bracketsFound == 0) && !openingFound) {
				beforeBrackets.append(c);
			}
		}
		List<String> list = new ArrayList<>();
		addIfNotEmpty(list, beforeBrackets.toString());
		addIfNotEmpty(list, newExpression.toString());
		addIfNotEmpty(list, afterBrackets.toString());
		return list;
	}

	private static boolean hasBrackets(String expression) {
		char[] array = expression.toCharArray();
		for (char c : array) {
			if (isBracket(c)) {
				return true;
			}
		}
		return false;
	}

	private static int countBrackesDepth(String expression) {
		int maxBracketDepth = 0;
		int currentBracketDept = 0;
		char[] array = expression.toCharArray();
		for (char c : array) {
			if (isOpeningBracket(c)) {
				currentBracketDept++;
			}
			if (isClosingBracket(c)) {
				if (maxBracketDepth < currentBracketDept) {
					maxBracketDepth = currentBracketDept;
				}
				currentBracketDept--;
			}
		}
		return maxBracketDepth;
	}
}
