package ld27.AI;

import ld27.player.Player;
import ld27.player.types.Ninja;

public class EnemyAI {
	public EnemyAI(){
		
	}
	public void tick(Player e, Player p){
		if (!(e.playerX < 2550 && e.playerX > 2510)){
			e.keyController.setAttacking(false);
			if (e.playerX > 4800) {
				e.keyController.setMovingLeft(true);
				e.keyController.setMovingLeft(true);
			} else if (e.playerX < 4800 && e.playerX > 4750){
				e.keyController.setJumpKey(true);
				e.keyController.setJumpReleased(false);
			} else if (e.playerX < 3920 && e.playerX > 3900){
				e.keyController.setJumpKey(true);
				e.keyController.setJumpReleased(false);
			} else if (e.playerX < 3790 && e.playerX > 3760){
				e.keyController.setJumpKey(true);
				e.keyController.setJumpReleased(false);
			} else if (e.playerX < 2765 && e.playerX > 2745){
				e.keyController.setJumpKey(true);
				e.keyController.setJumpReleased(false);
			} else if (e.playerX < 2550 && e.playerX > 2510){
				e.keyController.setMovingLeft(false);
				e.keyController.setLastMovingLeft(true);
			}
		} else {
			e.keyController.setMovingLeft(false);
			e.keyController.setLastMovingLeft(true);
			if (p instanceof Ninja){
				if (!((Ninja) p).isStealthed()){
					if (Math.abs(p.playerX-e.playerX) < 300 && Math.abs(p.playerY-e.playerY) < 10){
						if (p.playerX-e.playerX < 0){
							e.keyController.setLastMovingLeft(true);
							e.keyController.setAttacking(true);
						} else {
							e.keyController.setLastMovingLeft(false);
							e.keyController.setAttacking(true);
						}
					} else {
						e.keyController.setAttacking(false);
					}
				}
			} else {
				if (Math.abs(p.playerX-e.playerX) < 300 && Math.abs(p.playerY-e.playerY) < 10){
					if (p.playerX-e.playerX < 0){
						e.keyController.setLastMovingLeft(true);
						e.keyController.setAttacking(true);
					} else {
						e.keyController.setLastMovingLeft(false);
						e.keyController.setAttacking(true);
					}
				} else {
					e.keyController.setAttacking(false);
				}
			}
			
		}
	}
}
