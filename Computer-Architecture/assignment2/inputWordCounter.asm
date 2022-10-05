# This program 1.
#
#	use the dialog syscall (#54) to input a string from the user
#	call a function which counts the number of characters and number of words in the string and returns these in $v0 and $v1; store these in memory
#	output (console) the string and counts to the user (see example below)
#	repeat from 1 until the user enters a blank string or hits “cancel”
#	additionally, use $s1 somewhere in your function so that you must save it on the stack at the top of your function and restore it before the function exits; Of course this function could be written without using an s register, but this is good practice in using the stack.
#	output a dialog message (syscall #59) to say goodbye before the program ends 

#	

#Format

#label	.word	value

.data

# Golds Word and Character count
wordCount:	.word 0
charCount:	.word 0

# Holds Username
string:	.space	99

# Prompts
messagePrompt:	.asciiz "Enter some text: "
exitPrompt:	.asciiz	"Goodbye"
resultWordMsg:	.asciiz	"words"
resultCharMsg:	.asciiz	"characters \n"



	.text
loop:
	# 
	#pop up dialog
	# load msg prompt
	la $a0, messagePrompt
	la $a1, string # holds string
	li $a2, 99
	li $v0, 54
	syscall
	
	bnez $a1, exit
	
	jal counter # jumps to counter
	
	sw $v0, wordCount #assign # words in mem
	sw $v1, charCount # save char in to mem
	
	#display string
	li $v0, 4
	la $a0, string
	syscall
	
	# prints val
	li $v0, 1	
	lw $a0, wordCount	
	syscall
	
	#prints ress message
	li $v0, 4	
	la $a0, resultWordMsg
	syscall
	
	# prints int
	li $v0, 1
	lw $a0, charCount
	syscall
	
	#print string of the res message
	li $v0, 4	
	la $a0, resultCharMsg
	syscall
	
	
j loop # jumps back to the loop

exit:	#exits program
   	li $v0,59           # Dialog Modal
   	la $a0,exitPrompt       # Exit Prompt displays
   	syscall 
   	li $v0,10           #exit program
   	syscall

	#counter	
counter:
   	addi $sp,$sp 4           # add 4 bytes
   	sw $s1,0($sp)           # saves val into s1
  	
  	#load word,char,string
   	li $t0,0           
   	li $t1,0          
   	la $t2,string  
  
loop2:  # If statement
	lb $t3,($t2)           # we get a byte from the string
   	beqz $t3,endofloop           # if branch equal to zero we go to the next
   	bne $t3,32,loop3           # If brnach is not equal we go to loop3
   	addi $t0,$t0,1           # word counter increases
loop3:
	addi $t1,$t1,1           # Increase chars counter
   	addi $t2,$t2,1           # Increases string
   	b loop2           # branches to loop2
  
endofloop:
   	addi $t0,$t0,1           # increment for word
   	addi $t1,$t1,-1           # decrement in order to get the right chars
   	move $v0,$t0           # Move Word Count into v0
   	move $v1,$t1           # Move contents into $v1 with char
   	lw $s1,4($sp)           # revert s1 to what we had previously
   	addi $sp,$sp,4           # add 4 bytes to the stack pointer.
   	jr $ra               # return control to the caller
