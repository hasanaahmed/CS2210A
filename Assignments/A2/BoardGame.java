/**
 * 
 * @author hasanahmed
 * CS2210A Asn2
 * 19 October 2018
 *
 */
public class BoardGame {

	/**
	 * initalize variables
	 */
	private int board_size, empty_positions, max_levels;
	private char[][] gameBoard;
	private final char HUMAN = 'b', COMPUTER = 'o', EMPTY = 'g';
	
	/**
	 * 
	 * @param board_size
	 * @param empty_positions
	 * @param max_levels
	 */
	public BoardGame (int board_size, int empty_positions, int max_levels) {
		this.board_size = board_size;
		this.empty_positions = empty_positions;
		this.max_levels = max_levels;
		this.gameBoard = new char[board_size][board_size];
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				gameBoard[i][j] = 'g';
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private String boardAsString() {
		String boardGameStr = "";
		for (int row = 0; row < this.board_size; row++) {
			for (int col = 0; col < this.board_size; col++) {
				if (gameBoard[row][col] == 0) {
					boardGameStr += " ";
				}
				else {
					boardGameStr += this.gameBoard[row][col];
				}
			}
		}
		return boardGameStr;
	}
	
	/**
	 * 
	 * @return
	 */
	public HashDictionary makeDictionary() {
		HashDictionary newDict = new HashDictionary(7177);
		return newDict;
	}
	
	public int isRepeatedConfig(HashDictionary dict) {
		String boardGameStr = boardAsString();
		return dict.getScore(boardGameStr);	
	}
	
	/**
	 * 
	 * @param configurations
	 * @param score
	 */
	public void putConfig(HashDictionary configurations, int score) {
		String boardGameStr = boardAsString();
		Configuration newEntry = new Configuration(boardGameStr, score);
		try {
			configurations.put(newEntry);
		}
		catch (DictionaryException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @param symbol
	 */
	public void savePlay(int row, int col, char symbol) {
		if ((row == -1 || col == -1) || (row > board_size || col > board_size)) {
			System.out.println("You did not input correctly for rows and coloumns");	
		}
		else {
			this.gameBoard[row][col] = symbol;
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean positionIsEmpty (int row, int col) {
		if (this.gameBoard[row][col] == 'g') {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean tileOfComputer (int row, int col) {
		if (this.gameBoard[row][col] == 'o') {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean tileOfHuman (int row, int col) {
		if (this.gameBoard[row][col] == 'b') {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public boolean wins (char symbol) {
		for (int row = 0; row < board_size; row++) {
			for (int col = 0; col < board_size; col++) {
				if (gameBoard[row][col] == symbol) {
					if (checkVertical(col, row, symbol)) {
						return true;
					}
					if (checkHorizontal(col, row, symbol)) {
						return true;
					}
					if (checkDiagonalRight(row, symbol)) {
						return true;
					}
					if (checkDiagonalLeft(col, symbol)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param cr
	 * @param symbol
	 * @return
	 */
	private boolean checkDiagonalRight(int cr, char symbol) {
		int count = 0;
		for (int i = cr; i < board_size; i++) {
				if (gameBoard[i][i] == symbol)
					count++;
				else break;
		}
		return (count >= board_size);
	}
	
	/**
	 * 
	 * @param cr
	 * @param symbol
	 * @return
	 */
	private boolean checkDiagonalLeft(int cr, char symbol) {
		int count = 0;
		for (int i = board_size - 1; i >= cr; i--) {
				if (gameBoard[i][i] == symbol)
					count++;
				else break;
		}
		return (count >= board_size);
	}
	
	/**
	 * 
	 * @param col
	 * @param row
	 * @param symbol
	 * @return
	 */
	private boolean checkVertical(int col, int row, char symbol) {
		int count = 0;
		for (int i = col; i < board_size; i++) {
			if (gameBoard[row][i] == symbol) {
				count ++;
			}
			else {
				break;
			}
		}
		return (count >= board_size);
	}
	
	/**
	 * 
	 * @param col
	 * @param row
	 * @param symbol
	 * @return
	 */
	private boolean checkHorizontal(int col, int row, char symbol) {
		int count = 0;
		for (int i = row; i < board_size; i++) {
			if (gameBoard[i][row] == symbol) {
				count ++;
			}
			else {
				break;
			}
		}
		return (count >= board_size);
	}
	
	/**
	 * 
	 * @param symbol
	 * @param empty_positions
	 * @return
	 */
	public boolean isDraw(char symbol, int empty_positions) {
		int countEmpty = 0;
		if (wins(symbol)) {
			return false;
		}
		else {
			for(int row = 0; row < gameBoard.length; row++){
                for (int col = 0; col <gameBoard.length; col++){
                    if (gameBoard[row][col] == 'g') {
                        countEmpty++;
                        if (adjacent(row, col, symbol) == true) // if symbol is found adjacent, method returns false
                            return false;
                    }
                }
            }
        }
        return (countEmpty <= empty_positions);
	}
	
	/**
	 * 
	 * @param targetRow
	 * @param targetCol
	 * @param symbol
	 * @return
	 */
	private boolean adjacent(int targetRow, int targetCol, char symbol){
        int size = gameBoard.length;
        for (int row = targetRow - 1; row <= targetRow + 1; row++) {
            for (int col = targetCol - 1; col <= targetCol + 1; col++) {
                if (row <0 || row >= size || col <0 || col >= size || (col == targetCol && row == targetRow)) // checks if cell is out of bounds
                    continue;
                if (gameBoard[row][col] == symbol)
                    return true;
            }
        }
        return false;
    }
	
	/**
	 * 
	 * @param symbol
	 * @param empty_positions
	 * @return
	 */
	public int evalBoard(char symbol, int empty_positions) {
		if (wins(HUMAN) == true) {
			return 0;
		}
		else if (isDraw(symbol, empty_positions) == true) {
			return 2;
		}
		else if (wins(COMPUTER) == true) {
			return 3;
		}
		return 1;
	}
}
