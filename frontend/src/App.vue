

<template>
  <div id="app">
    <h1>Plane Shooter Game</h1>
    <canvas ref="gameCanvas" width="800" height="600"></canvas>
  </div>
</template>

<style>
#app {
  display: block;
}
canvas {
  background: #87CEEB;
}
</style>

<script>
export default {
  name: 'App',
  mounted() {
    this.initGame();
  },
  methods: {
    initGame() {
      console.log('Initializing game...');
      const canvas = this.$refs.gameCanvas;
      console.log('Canvas element:', canvas);
      
      if (!canvas) {
        console.error('Canvas element not found!');
        return;
      }
      
      const ctx = canvas.getContext('2d');
      console.log('Canvas context:', ctx);
      
      if (!ctx) {
        console.error('Could not get 2D context');
        return;
      }

      // Game state
      const gameState = {
        player: { x: 400, y: 500, width: 50, height: 30, speed: 5 },
        bullets: [],
        enemies: [],
        lastTime: 0
      };
      
      console.log('Game state initialized');
      
      // Draw player plane
      function drawPlayer() {
        ctx.fillStyle = 'blue';
        ctx.fillRect(gameState.player.x, gameState.player.y, gameState.player.width, gameState.player.height);
        
        // Draw cockpit
        ctx.fillStyle = 'cyan';
        ctx.fillRect(gameState.player.x + 10, gameState.player.y - 10, 30, 10);
      }
      
      // Game loop
      const gameLoop = (timestamp) => {
        const deltaTime = timestamp - gameState.lastTime;
        gameState.lastTime = timestamp;
        
        // Clear canvas
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        
        // Draw player
        drawPlayer();
        
        // Continue game loop
        requestAnimationFrame(gameLoop);
      };
      
      // Start game loop
      requestAnimationFrame(gameLoop);
      
      // Keyboard controls
      window.addEventListener('keydown', (e) => {
        if (e.key === 'ArrowLeft') gameState.player.x -= gameState.player.speed;
        if (e.key === 'ArrowRight') gameState.player.x += gameState.player.speed;
        if (e.key === 'ArrowUp') gameState.player.y -= gameState.player.speed;
        if (e.key === 'ArrowDown') gameState.player.y += gameState.player.speed;
        if (e.key === ' ') this.shootBullet(gameState);
      });
    },
    
    shootBullet(gameState) {
      gameState.bullets.push({
        x: gameState.player.x + gameState.player.width/2,
        y: gameState.player.y,
        speed: 10
      });
    }
  }
}
</script>

<style>
#app {
  font-family: Arial, sans-serif;
  text-align: center;
}

canvas {
  border: 1px solid #000;
  background-color: #87CEEB;
  margin-top: 20px;
}
</style>

