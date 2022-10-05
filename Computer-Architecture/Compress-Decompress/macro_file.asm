# macro

# Macro File: Create macros to 
# print an int, print a char, print a string, get a string from the user, open file, close file, read file, and allocate heap memory

# prints string
.macro print_str (%str)
    .data
str:.asciiz %str

    .text
    la	$a0,	str
    li	$v0,	4
    syscall
.end_macro

# prints int

.macro print_int(%int)
	li $v0, 1
	lw $a0, %int
	syscall
.end_macro
# prints char
.macro print_char(%char)
	.data
char: 	.byte %char
	.text
	li $v0, 11
	lb $a0, char
	syscall
.end_macro




# prints insides of register
.macro	print_register (%register)
	.text
	li	$v0, 4
	move	$a0, %register
	syscall
.end_macro

# user input
.macro	get_input (%x, %y)
	.text
	li	$v0, 8
	la	$a0, %x
	li	$a1, %y
	syscall
.end_macro

# file open
.macro	open_file(%name)
    #call func
    newLine(%name)
   
    la	$a0,	%name
    li	$a1,	0
    li	$v0,	13
    syscall
   
    move $s4,	$v0
    bgtz $v0,	end
   
    print_str("Error Opening File. Program Terminating.")
    j	endLoop
   
end:
.end_macro

#new line
.macro	newLine (%s)
    la	 $a0,	%s
    li	 $t0,	10
   
bytes:   
    lb	 $t1,	($a0)
    beq	 $t1,	$t0,	endLoop
    beqz $t1,	endLoop	
    addi $a0,	$a0,	1
    j	 bytes
    
endLoop:   
    sb	$zero,	($a0)
.end_macro

# close file
.macro	file_close
	li	$v0, 16
	move	$a0, $s4
	syscall
.end_macro

# read file
.macro	read_file(%buffer)
	li	$v0, 14
	move	$a0, $s4
	la	$a1, %buffer
	li	$a2, 1024
	syscall
	move	$s3, $v0
.end_macro

# mem allocation for heap
.macro	allocate_heap (%heap)
	li	$a0, 1024
	li	$v0, 9
	syscall
	sw	$v0, %heap
.end_macro
