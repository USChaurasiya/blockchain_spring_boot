package com.uma.blockchain.model;

import java.util.Date;

import com.uma.blockchain.util.BlockUtil;

public class Block {

	public String hash;

	public String previousHash;

	private String data;

	private long timeStamp;

	private int nonce;

	public Block( String data,String previousHash ) {
		super();
		this.previousHash = previousHash;
		this.data = data;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}

	public String calculateHash() {
		String calculateHash = BlockUtil
				.applySha256(previousHash 
						+ Long.toString(timeStamp) 
						+ Integer.toString(nonce) 
						+ data);
		return calculateHash;
	}

	public void mineBlock(int difficulty) {
		String target = BlockUtil.getDificultyString(difficulty);
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined !!!... " + hash);
	}

}
