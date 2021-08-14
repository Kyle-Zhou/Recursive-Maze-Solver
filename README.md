# Recursive-Maze-Solver
Recursive pathfinding algorithm to solve text based mazes simulating food delivery.

## Overview: 

- The first two values of the text file of a maze will always be length and width of the maze respectively
- Hashtags represent walls
- S represents the starting point
- Numbers represent destinations: lower numbers have higher priority

### ex: 
```
31
31
###############################
#           #       #       1 #
### ############# ### ### # ###
#1# #   # #         # # # #   #
# # # ### ### ### ##### ##### #
#       # #   #1  #       # # #
### ##### ### ### ### ##### # #
#       #   # #   # #         #
##### ### # ##### # ####### ###
# # #   # # #     #     #     #
# # # ### ### ##### ### # #####
# #               #   #       #
# # ### # ####### # ##### #####
#     # # #   #         # #   #
### # ####### ##### # ### # # #
#S# #   #   #     # #   # # # #
# # # ### ##### ### ##### ### #
#   # #   # #  1  #   #       #
# # # ### # # ##### # # # #####
# # #       #   # # #1# #     #
##### # # # # ### # ##### ### #
#   # # # # #     #   #     # #
# ##### ### # ######### ### ###
# #   # # #     #     #   #   #
# # ##### # ##### ########### #
#     #       #   #           #
# # # # ### ##### # ####### # #
# # #     # #           # # # #
# # ##### # ### # ### ### ### #
# #   #1  # #   #   #     #   #
###############################
```

- Scans for all possible solutions and outputs the optimal route (including between multiple destinations)
  -  x represents a tile that has been visited
  -  X represents a destination that has been visited
- saves output to PPM file

### ex:



