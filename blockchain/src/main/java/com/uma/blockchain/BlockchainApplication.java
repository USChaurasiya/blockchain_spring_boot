package com.uma.blockchain;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.uma.blockchain.model.Block;
import com.uma.blockchain.util.BlockUtil;

@SpringBootApplication
public class BlockchainApplication {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) {
		SpringApplication.run(BlockchainApplication.class, args);

		System.out.println("Mining First Block... ");
		addBlock(new Block("First Sample Block", "0"));
		
		System.out.println("Mining Second Block... ");
		blockchain.add(new Block("Second Sample Block",blockchain.get(blockchain.size()-1).hash));
		blockchain.get(1).mineBlock(difficulty);
		
		System.out.println("Mining Third Block... ");
		blockchain.add(new Block("Third Sample Block",blockchain.get(blockchain.size()-1).hash));
		blockchain.get(2).mineBlock(difficulty);

		System.out.println("\nBlockchain is Valid: " + isChainValid());

		String blockchainJson = BlockUtil.getJson(blockchain);
		System.out.println("\nComplete Block Chain Look Like: ");
		System.out.println(blockchainJson);
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}

		}
		return true;
	}

	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}
