# CarpTrader
A MiniFutures trend trader for all kinds of indices

--- VISION ---
Using Minifutures to leverage indice returns. Use volatility/derivate of trend in order to choose minifuture starting price correctly.
Or something simiar.

--- General trading algorithm ---

1. Split fund into X equally big piles, buy mfs and set timer for first re-purchase

2. Pick longest-in-market pile
3. Sell all mfs in pile
4. Measure trend and volatility
5. Calculate optimal mf price based on step 3
6. Buy as many mfs as you can up to (sum(X)+deposits) / X 
7. Set time until next purchase
8. Goto 2
