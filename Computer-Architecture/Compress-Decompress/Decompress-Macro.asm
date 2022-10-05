# This program compresses and decompress's file
# macro
.include "macro_file.asm"

	.data
fileName:	.space	64
buffer:		.space	1024
heap:		.word	0
ogSize:	.word	0
compSize:	.word	0

	.text
main:
	# Allocate 1024 bytes
	allocate_heap(heap)
	
	# Prints Prompt, Gets User Input from console.
	print_str("Please enter file name to compress or <enter> to exit: ")
	get_input(fileName, 64)	# file name
	print_char('\n')
	
	# If Enter -> End
	lb	$t0, fileName
	beq	$t0, '\n', endLoop
	
	# Open & Read
	open_file(fileName)
	read_file(buffer)
	# store size
	sw	$s3, ogSize	

	file_close
	
	# Printing out the data from the file
	print_str("Original data: \n")
	print_char('\n')
	print_str("Compressed data: \n")
	#load
	la	$a0, buffer
	lw	$a1, heap
	lw	$a2, ogSize
	jal	compress
	
	lw	$t0, heap
	# print data
	print_register($t0)
	print_char('\n')
	print_str("Uncompressed data: \n")

	lw	$a0, heap
	lw	$a1, ogSize
	jal decompress # jump to decompress
	
	# Print Strings
	print_char('\n')
	print_str("Original file size: ")
	print_int(ogSize)
	print_char('\n')
	print_str("Compressed file size: ")
	print_int(compSize)
	print_char('\n')
	
	j	main
	
endLoop:
	li	$v0, 10		# Exit program
	syscall



compress:
	#xounter and size
	li	$t0, 0
	li	$s4, 0
compLoop1:
	# if less than branch to endcomp
	bge	$t0, $a2, endCompression
	li	$t1, 1
	
compLoop2:
	# load byte then go next
	add	$t2, $a0, $t0
	lb	$s1, ($t2)
	addi	$t3, $t0, 1
	add	$t3, $a0, $t3
	lb	$s2, ($t3)
	# Branches to store if conditions are met, increment
	bge	$t0, $a1, store	
	bne	$s1, $s2, store	
	addi	$t0, $t0, 1
	addi	$t1, $t1, 1
	j	compLoop2

	# store our string	
store:
	# store the string
	sb	$s1, ($a1)
	addi	$s4, $s4, 1
	addi	$a1, $a1, 1
	
	# branches if count is above 9
	bgt	$t1, 9, doubleDigits
	
	# calculate ascii and store byte
	addi	$t1, $t1, 48
	sb	$t1, ($a1)
	
	# Increment
	addi	$s4, $s4, 1
	addi	$a1, $a1, 1
	addi	$t0, $t0, 1
	j	compLoop1
	
doubleDigits:
	li	$t6, 10
	div	$t1, $t6
	# move value, store value
	mflo	$t4
	addi	$t4, $t4, 48
	sb	$t4, ($a1)
	addi	$s4, $s4, 1
	
	# move value, store value
	mfhi	$t5
	addi	$t5, $t5, 48
	addi	$a1, $a1, 1
	sb	$t5, ($a1)
	
	# increment
	addi	$a1, $a1, 1
	addi	$t0, $t0, 1
	j	compLoop1
	
endCompression:
	#save compressed
	sw	$s4, compSize
	jr 	$ra


decompress:
	# load data
	lb	$t0, ($a0)
	lb	$t1, 1($a0)
	lb	$t2, 2($a0)
	
	# check / branch
	beq	$a1, $0, return
	blt	$t2, 48, singleDigit
	ble	$t2, 57, doubleDigit
	
# Print single #'s
singleDigit:
	addi	$t1, $t1, -48
	addi	$sp, $sp, -4  
	sw	$a0, ($sp)
	move	$t3, $t1
	
SingleLoop:
	beq	$t3, $0, exitSingleDigit
	move	$a0, $t0
	li	$v0, 11	
	syscall
	addi	$t3, $t3, -1
	j SingleLoop
	
exitSingleDigit:
	lw	$a0, ($sp)
	addi	$sp, $sp, 4
	sub	$a1, $a1, $t1
	addi	$a0, $a0, 2
	j	decompress

# Print double #s
doubleDigit:
	addi	$t1, $t1, -48
	addi	$t2, $t2, -48
	mul	$t1, $t1, 10
	add	$t1, $t1, $t2
	
	addi	$sp, $sp, -4  
	sw	$a0, ($sp)
	move	$t3, $t1
doubleDigitLoop:
	beq	$t3, $0, exitDoubleDigit
	move	$a0, $t0
	li	$v0, 11
	syscall
	addi	$t3, $t3, -1
	j doubleDigitLoop
exitDoubleDigit:
	lw	$a0, ($sp)
	addi	$sp, $sp, 4
	sub	$a1, $a1, $t1
	addi	$a0, $a0, 3
	j	decompress

return:
	jr	$ra
