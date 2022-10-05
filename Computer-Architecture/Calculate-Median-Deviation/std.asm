# - read the input file “input.txt” into a buffer in memory
# - extract the string “numbers”, convert them to integers and store in an array
# - print the integers to console
# - sort the integers in place using selection sort
# - print the sorted integers to console
# - calculate the mean, median, and standard deviation, printing results to the console

.data

arr:	.space 80 #80 bytes for arr
len:	.word 20 
mean:	.float 0.0
median:	.word 0
sd:	.float 0,0
fname:	.asciiz "input.txt"
	#.align 2
buff:	.space 80
msg_b: .asciiz "The array before: \t"
msg_a: .asciiz "The array after: \t"
msg1:	.asciiz "The mean is: "
msg2:	.asciiz "The Median Is: "
msg3:	.asciiz "The standard deviation is: "
msge:	.asciiz "Error reading file. Program terminated"

.text
	# read 20 integers from file
main:	la $a0, fname
	la $a1, buff
	
	#jal read_buff
#	bgt $v0, $0, stp2
	
	li $v0, 4
	la $a0, msge
	
	syscall
	
	j exit
	
# stp2:	la $a0, arr
#	lw $a1, len
	
	
read: 
	move $t0, $a1 #$a1 to the address of the buffer where data is stored
	li $v0, 13	#sys call to open file
	li $a1, 0 #flag for reading
	syscall
	
	
	li $v0, 14 #syscall to read from file
	move $a0, $s0 #set $a0 equal to the address of the filename
	move $a1, $t0
	li $a2, 80
	syscall
	
	#close file
	li $v0, 16
	move $a0, $s0
	syscall
	move $v0, $s0
	
exit:	#exits program
   	li $v0,10           #exit program
   	syscall
