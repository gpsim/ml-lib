package com.spuerh.hz.mllib.struct.base.matrix;

import java.math.BigInteger;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is sparse matrix for integer datetype. Most of the data in sparse matrix is a default
 * value.
 * The table is in row-major order.
 */
public class IntegerSparseMatrix {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IntegerSparseMatrix.class);

	private int rows = 0;
	private int cols = 0;
	private int default_val = 0;
	private Hashtable<BigInteger, Integer> table;

	public IntegerSparseMatrix(int rows, int cols, int val) {
		if (rows >= 0) {
			this.rows = rows;
		} else {
			LOGGER.error("Illegal Parameters, the size of row in sparse matrix should be greater than 0,but is "
					+ this.rows);
		}
		if (cols >= 0) {
			this.cols = cols;
		} else {
			LOGGER.error("Illegal Parameters, the size of column in sparse matrix should be greater than 0,but is "
					+ this.cols);
		}
		this.default_val = val;
		table = new Hashtable<BigInteger, Integer>();
	}

	/**
	 * Get the data of the position[row,col].
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public int get(int row, int col) {
		if (row >= 0 && row < this.rows && col >= 0 && col < this.cols) {
			BigInteger rowBigInteger = BigInteger.valueOf(row);
			BigInteger colBigInteger = BigInteger.valueOf(col);
			BigInteger id = rowBigInteger.multiply(
					BigInteger.valueOf(this.cols)).add(colBigInteger);
			if (this.table.containsKey(id)) {
				return this.table.get(id).intValue();
			}
		} else {
			throw new IllegalArgumentException("Illegal Parameters,(" + row
					+ "," + col + ") Is not in [(0,0),(" + this.rows + ","
					+ this.cols + ")])");
		}
		return default_val;
	}

	/**
	 * Check whether the data of the position [row,col] has been set.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean exists(int row, int col) {
		if (row >= 0 && row < this.rows && col >= 0 && col < this.cols) {
			BigInteger rowBigInteger = BigInteger.valueOf(row);
			BigInteger colBigInteger = BigInteger.valueOf(col);
			BigInteger id = rowBigInteger.multiply(
					BigInteger.valueOf(this.cols)).add(colBigInteger);
			if (this.table.containsKey(id)) {
				return true;
			}
		} else {
			throw new IllegalArgumentException("Illegal Parameters,(" + row
					+ "," + col + ") Is not in [(0,0),(" + this.rows + ","
					+ this.cols + ")])");
		}
		return false;
	}

	/**
	 * 
	 * @param rows
	 * @param cols
	 * @param val
	 * @return whether succeed
	 */
	public boolean set(int row, int col, int val) {
		if (row >= 0 && row < this.rows && col >= 0 && col < this.cols) {
			BigInteger rowBigInteger = BigInteger.valueOf(row);
			BigInteger colBigInteger = BigInteger.valueOf(col);
			BigInteger id = rowBigInteger.multiply(
					BigInteger.valueOf(this.cols)).add(colBigInteger);
			this.table.put(id, val);
			return true;
		} else {
			throw new IllegalArgumentException("Illegal Parameters,(" + row
					+ "," + col + ") Is not in [(0,0),(" + this.rows + ","
					+ this.cols + ")])");
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return cols;
	}
}
