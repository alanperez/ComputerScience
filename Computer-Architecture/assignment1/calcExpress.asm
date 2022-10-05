# This program gets the user's name and 3 integers
# then calculates 3 expressions and displays the results



#Format

#label	.word	value

.data
# Assign input values to variablbes a,b,c	
a:	.word	0
b:	.word	0
c:	.word 	0

#Output Values
outA:	.word	0
outB:	.word	0
outC:	.word	0

# Holds Username
name:	.space	20

# Prompts
namePrompt:	.asciiz	"What is your name?: "
intPrompt:	.asciiz	"Please enter an integer between 1-100: "
resultMsg:	.asciiz	"your answers are: "

# Text Section
.text
	#get and store user name
	li	$v0, 4	# load appropriate system call code into register $v0
	la	$a0, namePrompt # load address of string to be printed into $a0
	syscall
	
	li	$v0, 8 #get string from user ( syscall 8 read string)
	la	$a0, name
	li	$a1, 19
	syscall
	
	#get three ints from user
	
	li	$v0, 4
	la	$a0, intPrompt #Displays integer question prompt
	syscall
	
	li	$v0, 5	#get int from user (reads integer)
	syscall
	sw $v0, a	#Stores user input into variable a
	
	#INT BB
	li	$v0, 4
	la	$a0, intPrompt
	syscall
	
	li	$v0, 5	#get int from user
	syscall
	sw $v0, b
	
	#INT C
	li	$v0, 4
	la	$a0, intPrompt
	syscall
	
	li	$v0, 5	#get int from user
	syscall
	sw $v0, c
	
	#Calc - Find the sum of the ints and store it
	lw	$s1, a
	lw	$s2, b
	lw	$s3, c
	
	add	$t0, $s1, $s1 #(use a+a for 2a) and store the result
	add	$t0, $t0, 4 #2a + 4 
	sub	$t0, $t0, $s3 # 2a+4-c
	sw	$t0, outA #stores calc in outA variable
	
	#Calc Output B
	sub	$t0, $s2, $s3 # let t0 = b-c 
	add	$t0, $t0, $s1 # b - c + a
	sub	$t0, $t0, 2 #  b - c + a + 2
	sw	$t0, outB
	
	#Calc Output C
	add	$t0, $s1, 3 # a + 3
	sub	$t0, $t0, $s2 # (a+3) - b
	add	$t0, $t0, 1 # a + 3 - b + 1
	add	$t0, $t0, $s3 # a + 3 - b - 1 + c
	add	$t0, $t0, 3 # a + 3 - b - 1 + c +3
	sw	$t0, outC
	
	
	# Console Output
	
	li $v0, 4
	la $a0, name
	syscall
	
	li $v0, 4
	la $a0, resultMsg
	syscall
	
	#Calculated A
	li $v0, 1
	lw $a0, outA #loading output A
	syscall
	
	# space
	li $a0, 32 # ascii number for space
	li $v0, 11
	syscall
	
	#Calculated B
	li $v0, 1
	lw $a0, outB #loading output BB
	syscall
	
	# space
	li $a0, 32
	li $v0, 11
	syscall
	
	#Calculated C
	li $v0,	1
	lw $a0, outC #loading output C
	syscall
	
	#Exit
	li $v0, 10
	syscall

# Test Runs

# Default Test Run to verify it looks the same as requirements

#	What is your name?: Alan
#	Please enter an integer between 1-100: What is your name?: Alan
#	Please enter an integer between 1-100: 10
#	Please enter an integer between 1-100: 20
#	Please enter an integer between 1-100: 30
#	Alan
#	your answers are: -6 -2 27
#	-- program is finished running --


# Test 1

#	What is your name?: Alan
#	Please enter an integer between 1-100: 5
#	Please enter an integer between 1-100: 10
#	Please enter an integer between 1-100: 15
#	Alan
#	your answers are: -1 -2 17
#	-- program is finished running --

# Test 2


#	What is your name?: Alan
#	Please enter an integer between 1-100: 100
#	Please enter an integer between 1-100: 11
#	Please enter an integer between 1-100: 25
#	Alan
#	your answers are: 179 84 121
#	-- program is finished running --

