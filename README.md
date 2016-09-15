# Chess

This program is a command based chess program written in Java 8 with the Spring framework. It includes all rules of chess which are e.g.:
* The program ascertains the winner in case of a checkmate
* Knows different types of stalemate. A stalemate happens for example if both players have only their king in the game or when 50 turns long no pawn was set or no figure was hit. Furthermore there exist for every type a specific message.
* When a pawn reaches the baseline of the other player, the owner can change it to an other figure.
* It supports special moves like castling and en passant

For starting the program you can use the "start.bat" file for Windows or the "start.sh" file for Linux.

To see a list of the commands type in "help" during the program is running. The game can only be reset if a match is finished.

The language which is shown to the user is English because the program has no I18n support.

If there are any questions about the program you are welcome to ask me.