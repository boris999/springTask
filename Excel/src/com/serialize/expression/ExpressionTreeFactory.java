package com.serialize.expression;

import java.util.EmptyStackException;
import java.util.Stack;

import com.model.expression.BinaryOperator;
import com.model.expression.BinaryOperatorNode;
import com.model.expression.Condition;
import com.model.expression.ExpressionTreeNode;
import com.model.expression.ReferenceNode;
import com.model.expression.ValueNode;
import com.model.table.CellReference;

public final class ExpressionTreeFactory {

	private ExpressionTreeFactory() {
	}

	public static ExpressionTreeNode<CellReference> parseExpression(String expression) {
		expression = fixToPower(removeSigns(removeEmptyCheckBracketsAndStartEnd(expression)));
		Stack<ExpressionTreeNode<CellReference>> stack = new Stack<>();
		Stack<BinaryOperator> operatorStack = new Stack<>();
		char[] array = expression.toCharArray();
		StringBuilder sb = new StringBuilder("");
		Condition hasLetter = Condition.NOT_EVALUATED;
		Condition hasDigit = Condition.NOT_EVALUATED;
		boolean missingRight = false;
		boolean startingOperatorConsumed = false;
		boolean missingRightBracketNotClosed = false;
		int openingBracketsInStack = 0;
		for (char c : array) {
			hasLetter = hasLetter(c, hasLetter);
			hasDigit = hasDigit(c, hasDigit);
			// if operator -> +-*/^()
			if (isOperator(c)) {
				// if starts with -
				if (!startingOperatorConsumed && stack.isEmpty() && sb.toString().equals("") && !isBracket(c)) {
					sb.append("0");
					startingOperatorConsumed = true;
					hasDigit = Condition.TRUE;
				}
				boolean isPriorityOperator = isPriorityOperator(c);
				int operatorstackSize = operatorStack.size();
				if (missingRight && !missingRightBracketNotClosed && !isOpeningBracket(c)) {// no right node
					if (!isEmpty(sb)) {
						ExpressionTreeNode<CellReference> previous = stack.pop();
						sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
						hasLetter = Condition.NOT_EVALUATED;
						hasDigit = Condition.NOT_EVALUATED;
						ExpressionTreeNode<CellReference> lastNode = stack.pop();
						((BinaryOperatorNode<CellReference>) previous).setRigth(lastNode);
						stack.push(previous);
						if (!isClosingBracket(c)) {
							operatorStack.push(getOperator(c));
						} else {
							BinaryOperator last = operatorStack.pop();
							sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
							hasLetter = Condition.NOT_EVALUATED;
							hasDigit = Condition.NOT_EVALUATED;
							while (last != BinaryOperator.BRACKET) {
								ExpressionTreeNode<CellReference> right = stack.pop();
								ExpressionTreeNode<CellReference> left = stack.pop();
								BinaryOperatorNode<CellReference> tempNode = new BinaryOperatorNode<>(left, right, last);
								stack.push(tempNode);
								last = operatorStack.pop();
							}
							openingBracketsInStack--;
						}
						missingRight = false;
					} else {
						ExpressionTreeNode<CellReference> last = stack.pop();
						ExpressionTreeNode<CellReference> beforeLast = stack.pop();
						((BinaryOperatorNode<CellReference>) beforeLast).setRigth(last);
						stack.push(beforeLast);
						missingRight = false;
						operatorStack.push(getOperator(c));
					}
				} else if (!isPriorityOperator && ((operatorstackSize - openingBracketsInStack) == 0) && !isBracket(c)) {
					sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
					hasLetter = Condition.NOT_EVALUATED;
					hasDigit = Condition.NOT_EVALUATED;
					operatorStack.push(getOperator(c));
				} else if (!isPriorityOperator && ((operatorstackSize - openingBracketsInStack) > 0) && !isBracket(c)) {
					sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
					hasLetter = Condition.NOT_EVALUATED;
					hasDigit = Condition.NOT_EVALUATED;
					if (operatorStack.peek() != BinaryOperator.BRACKET) {
						ExpressionTreeNode<CellReference> right = stack.pop();
						ExpressionTreeNode<CellReference> left = stack.pop();
						BinaryOperator previousOperator = operatorStack.pop();
						BinaryOperatorNode<CellReference> node = new BinaryOperatorNode<>(left, right, previousOperator);
						stack.push(node);
					}
					operatorStack.push(getOperator(c));
				} else if (isPriorityOperator) {
					if (!isEmpty(sb)) {
						sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
						hasLetter = Condition.NOT_EVALUATED;
						hasDigit = Condition.NOT_EVALUATED;
					}
					ExpressionTreeNode<CellReference> left = stack.pop();
					BinaryOperator operator = getOperator(c);
					BinaryOperatorNode<CellReference> node = new BinaryOperatorNode<>(left, null, operator);
					stack.push(node);
					missingRight = true;
				} else if (isOpeningBracket(c)) {
					if (!isEmpty(sb)) {
						sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
						hasLetter = Condition.NOT_EVALUATED;
						hasDigit = Condition.NOT_EVALUATED;
					}
					operatorStack.push(getOperator(c));
					openingBracketsInStack++;
					if (missingRight) {
						missingRightBracketNotClosed = true;
					}
				} else if (isClosingBracket(c)) {
					BinaryOperator last = operatorStack.pop();
					sb = putReferenceOrTreeNodeInStack(stack, sb, hasLetter, hasDigit);
					hasLetter = Condition.NOT_EVALUATED;
					hasDigit = Condition.NOT_EVALUATED;
					while (last != BinaryOperator.BRACKET) {
						ExpressionTreeNode<CellReference> right = stack.pop();
						ExpressionTreeNode<CellReference> left = stack.pop();
						BinaryOperatorNode<CellReference> tempNode = new BinaryOperatorNode<>(left, right, last);
						stack.push(tempNode);
						try {
							last = operatorStack.pop();
						} catch (EmptyStackException e) {
							break;
						}
					}
					if (missingRightBracketNotClosed) {
						missingRight = true;
						missingRightBracketNotClosed = false;
						ExpressionTreeNode<CellReference> lastNode = stack.pop();
						ExpressionTreeNode<CellReference> previous = stack.pop();
						((BinaryOperatorNode<CellReference>) previous).setRigth(lastNode);
						stack.push(previous);
					}
					openingBracketsInStack--;
				}
			} else {// digit or number
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
			ExpressionTreeNode<CellReference> last = stack.pop();
			ExpressionTreeNode<CellReference> beforelast = stack.pop();
			if ((beforelast instanceof BinaryOperatorNode) && (((BinaryOperatorNode<CellReference>) beforelast).getRight() == null)) {
				((BinaryOperatorNode<CellReference>) beforelast).setRigth(last);
				stack.push(beforelast);
			} else {
				BinaryOperator lastOperator = operatorStack.pop();
				BinaryOperatorNode<CellReference> node = new BinaryOperatorNode<>(beforelast, last, lastOperator);
				stack.push(node);
			}
		}
		return stack.pop();

	}

	private static StringBuilder putReferenceOrTreeNodeInStack(Stack<ExpressionTreeNode<CellReference>> stack, StringBuilder sb, Condition hasLetter,
			Condition hasDigit) {
		if (hasLetter.equals(Condition.TRUE) && hasDigit.equals(Condition.TRUE)) {
			stack.push(new ReferenceNode<>(CellNameTransformer.convertCellNameToIndex(sb.toString())));
		} else if (hasLetter.equals(Condition.FALSE) && hasDigit.equals(Condition.TRUE)) {
			stack.push(new ValueNode<CellReference>(Double.parseDouble(sb.toString())));
		}
		return new StringBuilder("");

	}

	private static boolean isOperator(char c) {
		return (c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '^') || (c == '(') || (c == ')');
	}

	private static boolean isAritmeticOperator(char c) {
		return (c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '^');
	}

	private static boolean isPlusOrMinus(char c) {
		return (c == '+') || (c == '-');
	}

	private static boolean isPriorityOperator(char c) {
		return (c == '*') || (c == '/') || (c == '^');
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

	private static boolean isEmpty(StringBuilder sb) {
		return sb.toString().equals("");
	}

	private static String removeSigns(String expression) {
		while (hasTwoNeighborPlusMinusSigns(expression)) {
			expression = removeSign(expression);
		}

		StringBuilder sb = new StringBuilder(expression);
		while (sb.charAt(0) == '+') {
			sb.deleteCharAt(0);
		}
		return sb.toString();

	}

	private static String removeSign(String expression) {
		StringBuilder sb = new StringBuilder();
		char[] array = expression.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if ((i < (array.length - 1)) && isPlusOrMinus(array[i]) && isPlusOrMinus(array[i + 1])) {
				char result = calculatePlusOrMinus(array[i], array[i + 1]);
				sb.append(result);
				if ((i + 2) < array.length) {
					for (int j = i + 2; j < array.length; j++) {
						sb.append(array[j]);
					}
				}
				break;
			}
			sb.append(array[i]);
		}
		return sb.toString();
	}

	private static char calculatePlusOrMinus(char first, char second) {
		if (first == '+') {
			if (second == '-') {
				return '-';
			} else {
				return '+';
			}
		} else {
			if (second == '-') {
				return '+';
			} else {
				return '-';
			}
		}
	}

	private static boolean hasTwoNeighborPlusMinusSigns(String expression) {
		char[] array = expression.toCharArray();
		boolean previous = false;
		for (char c : array) {
			if (previous) {
				if (isPlusOrMinus(c)) {
					return true;
				} else {
					previous = false;
				}
			} else {
				previous = isPlusOrMinus(c);
			}
		}
		return false;
	}

	private static String removeEmptyCheckBracketsAndStartEnd(String expression) {
		char[] array = expression.toCharArray();
		StringBuilder sb = new StringBuilder();
		int numberOfBrackets = 0;
		for (char c : array) {
			if (numberOfBrackets < 0) {
				throw new IllegalArgumentException("Invalid expression");
			}
			if (c == ' ') {
				continue;
			} else {
				sb.append(c);
			}
			if (isClosingBracket(c)) {
				numberOfBrackets--;
			}
			if (isOpeningBracket(c)) {
				numberOfBrackets++;
			}
		}
		if ((numberOfBrackets != 0) || isPriorityOperator(sb.charAt(0)) || isAritmeticOperator(sb.charAt(sb.length() - 1))) {
			throw new IllegalArgumentException("Invalid expression");
		}
		return sb.toString();
	}

	private static String fixToPower(String expression) {
		StringBuilder sb = new StringBuilder();
		String[] powerArray = expression.split("\\^");
		if (powerArray.length == 1) {
			return powerArray[0];
		} else if (powerArray.length == 2) {
			sb.append(putOpeningBracket(powerArray[0]));
			sb.append("^");
			sb.append(putClosingBracket(powerArray[1]));
			return sb.toString();
		} else {
			sb.append(putOpeningBracket(powerArray[0]));
			for (int i = 1; i < (powerArray.length - 1); i++) {
				sb.append("^");
				String current = powerArray[i];
				if (containsArithmeticSign(current)) {
					current = putOpeningBracket(powerArray[i]);
					current = putClosingBracket(current);
				}
				sb.append(current);
			}
			sb.append("^");
			sb.append(putClosingBracket(powerArray[powerArray.length - 1]));
			return sb.toString();
		}
	}

	private static String putOpeningBracket(String word) {
		char[] array = word.toCharArray();
		StringBuilder sb = new StringBuilder();
		boolean bracketPut = false;
		for (int i = array.length - 1; i >= 0; i--) {
			if (isAritmeticOperator(array[i]) && !bracketPut) {
				sb.append('(');
				bracketPut = true;
			}
			sb.append(array[i]);
		}
		if (!bracketPut) {
			sb.append('(');
		}
		return sb.reverse().toString();
	}

	private static String putClosingBracket(String word) {
		char[] array = word.toCharArray();
		StringBuilder sb = new StringBuilder();
		boolean bracketPut = false;
		for (char c : array) {
			if (isAritmeticOperator(c) && !bracketPut) {
				sb.append(')');
				bracketPut = true;
			}
			sb.append(c);
		}
		if (!bracketPut) {
			sb.append(')');
		}
		return sb.toString();
	}

	private static boolean containsArithmeticSign(String expression) {
		return expression.contains("+") || expression.contains("-") || expression.contains("*") || expression.contains("/");
	}
}
