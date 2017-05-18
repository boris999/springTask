package com.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.script.ScriptException;

import com.model.expression.ReferenceNode;
import com.model.table.Table;

public class ForTest {

	public static void main(String[] args) throws ScriptException {
		Table t = new Table();
		// System.out.println(t.getColumnNames(96));

		Stack<Node> stack = new Stack<>();
		String forTest = "(B3-B1)*A4/B9-2";
		String forTest2 = "B3-B1*A4";
		List<Node> nodeList = transform(forTest2);
		Node one = nodeList.get(0);
		Node two = nodeList.get(1);
		Node three = nodeList.get(2);
		Node four = nodeList.get(3);
		Node five = nodeList.get(4);
		List<Node> nodes = new ArrayList<>();
		nodes.add(one);
		nodes.add(two);
		nodes.add(three);
		nodes.add(four);
		nodes.add(five);

		Node last = buildNode(nodes);
		System.out.println(nodeList);

	}

	// ((A1+5)/2+(A8-B3))*4
	public List<String> removeBrackets(String s) {
		List<String> list = new ArrayList<>();
		char[] array = s.toCharArray();
		int brackets = 0;
		// boolean expectClosing = false;
		StringBuilder sb = new StringBuilder("");
		int maxBrackets = this.countBrackets(s);
		for (char c : array) {
			if (c == ' ') {
				continue;
			}
			if (!isBracket(c)) {
				sb.append(c);
			}
			if (c == '(') {
				// if (brackets > 0) {
				sb = this.addToList(list, sb);
				// }
				brackets++;

			}
			if ((c == ')')) {
				sb = this.addToList(list, sb);
				brackets--;
			}

		}
		if (!sb.toString().equals("")) {
			list.add(sb.toString());
		}

		return list;
	}

	private StringBuilder addToList(List<String> list, StringBuilder sb) {
		if (!sb.toString().equals("")) {
			list.add(sb.toString());
			sb = new StringBuilder("");
		}
		return sb;
	}

	private static List<Object> transform(String forTest2) {
		List<Object> nodeList = new ArrayList<>();
		char[] array = forTest2.toCharArray();
		StringBuilder sb = new StringBuilder("");
		int bracketCounter = 0;
		int maxbracketCounter = 0;
		for (char c : array) {
			if (c == ' ') {
				continue;
			}
			if (isCellOrValue(c) && !isBracket(c)) {
				sb.append(c);
			} else {
				if (sb.toString().equals("")) {
					nodeList.add(String.valueOf(c));
				} else {
					ReferenceNode rnt = new ReferenceNode();
					rnt.setCellName(sb.toString());
					sb = new StringBuilder("");
					nodeList.add(rnt);
					nodeList.add(String.valueOf(c));
				}
			}
			// if (isOpeningBracket(c)) {
			// bracketCounter++;
			// if (bracketCounter > maxbracketCounter) {
			// maxbracketCounter = bracketCounter;
			// }
			// }
			// if (isClosingBracket(c)) {
			// bracketCounter--;
			// }
		}
		if (!sb.toString().equals("")) {
			nodeList.add(new Node(sb.toString()));
		}
		return nodeList;
	}

	public int countBrackets(String expression) {
		int bracketCounter = 0;
		int maxbracketCounter = 0;
		char[] array = expression.toCharArray();
		for (char c : array) {
			if (c == ' ') {
				continue;
			}
			if (isOpeningBracket(c)) {
				bracketCounter++;
				if (bracketCounter > maxbracketCounter) {
					maxbracketCounter = bracketCounter;
				}
			}
			if (isClosingBracket(c)) {
				bracketCounter--;
			}
		}
		return maxbracketCounter;
	}

	private static Node buildNode(List<Node> nodes) {
		boolean hasPriorityOperation = false;
		for (Node node : nodes) {
			if (isPriorityOperation(node)) {
				hasPriorityOperation = true;
				break;
			}
		}
		Stack<Node> tempStack = new Stack<>();
		if (hasPriorityOperation) {
			boolean secondMissing = false;
			for (Node node : nodes) {
				if (!isPriorityOperation(node)) {
					if (!secondMissing) {
						tempStack.push(node);
					} else {
						Node previous = tempStack.pop();
						Node current = node;
						previous.setRight(current);
						tempStack.push(previous);
						secondMissing = true;
					}
				} else {
					Node previous = tempStack.pop();
					Node current = node;
					current.setLeft(previous);
					tempStack.push(current);
					secondMissing = true;
				}
			}

		} else {
			boolean secondMissing = false;
			if (nodes.get(0).getName().equals("-")) {
				Node firstDummy = new Node("0");
				firstDummy.setValue(0.0);
				nodes.add(firstDummy);
			}
			for (Node node : nodes) {
				if (!isOperator(node)) {
					if (!secondMissing) {
						tempStack.push(node);
					} else {
						Node current = node;
						Node previous = tempStack.pop();
						previous.setLeft(current);
						tempStack.push(previous);
						secondMissing = false;
					}

				} else {
					Node current = node;
					Node previous = tempStack.pop();
					current.setLeft(previous);
					tempStack.push(current);
					secondMissing = true;

				}
			}

		}

		if (tempStack.size() == 1) {
			return tempStack.pop();
		}
		ArrayList<Node> tempNode = new ArrayList<>();

		while (!tempStack.isEmpty()) {
			tempNode.add(0, tempStack.pop());
		}
		return buildNode(tempNode);

	}

	private static boolean isOperator(Node node) {
		String name = node.getName();
		return name.equals("+") || name.equals("-") || name.equals("*") || name.equals("/") || name.equals("^");
	}

	private static boolean isCellOrValue(char c) {
		if ((c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '^')) {
			return false;
		}
		return true;
	}

	private static boolean isPriorityOperation(Node node) {
		String name = node.getName();
		return name.equals("*") || name.equals("/") || name.equals("^");
	}

	private static boolean isPriorityOperation(char c) {
		return (c == '*') || (c == '/') || (c == '^');
	}

	private static boolean isOpeningBracket(Node node) {
		String name = node.getName();
		return name.equals("(");
	}

	private static boolean isOpeningBracket(char c) {
		if ((c == '(')) {
			return true;
		}
		return false;
	}

	private static boolean isClosingBracket(Node node) {
		String name = node.getName();
		return name.equals(")");
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
}
