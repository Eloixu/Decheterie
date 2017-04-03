package com.hw;

public class gpio {
	public static final int PAD_GPIOA_0  = 154;
	public static final int PAD_GPIOB_0  = 130;
	public static final int PAD_GPIOC_0  = 105;
	public static final int PAD_GPIOD_0  = 95;
	public static final int PAD_GPIOE_0  = 13;
	public static final int PAD_GPIOX_0  = 41;
	public static final int PAD_GPIOY_0  = 25;
	public static final int PAD_GPIOZ_0  = 0;
	public static final int PAD_GPIOAO_0 = 182;
	
	public static final int PAD_GPIOA  = 0;
	public static final int PAD_GPIOB  = 1;
	public static final int PAD_GPIOC  = 2;
	public static final int PAD_GPIOD  = 3;
	public static final int PAD_GPIOE  = 4;
	public static final int PAD_GPIOX  = 5;
	public static final int PAD_GPIOY  = 6;
	public static final int PAD_GPIOZ  = 7;
	public static final int PAD_GPIOAO = 8;
	
	public static final int last_error = -99;
	
	public static int get_gpio_num(int group, int num){
		switch(group) {
			case PAD_GPIOA:
				return PAD_GPIOA_0 + num;
			case PAD_GPIOB:
				return PAD_GPIOB_0 + num;
			case PAD_GPIOC:
				return PAD_GPIOC_0 + num;
			case PAD_GPIOD:
				return PAD_GPIOD_0 + num;
			case PAD_GPIOE:
				return PAD_GPIOE_0 + num;
			case PAD_GPIOX:
				return PAD_GPIOX_0 + num;
			case PAD_GPIOY:
				return PAD_GPIOY_0 + num;
			case PAD_GPIOZ:
				return PAD_GPIOZ_0 + num;
			case PAD_GPIOAO:
				return PAD_GPIOAO_0 + num;
			default:
				;
		}
		return last_error;
	}
	
	public static int gpio_check(int group, int num) {
		if(group < 0 || group > 8) {
			return -9;
		}
		
		switch(group) {
			case PAD_GPIOA:// gpio_a
				if(num <= 27 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOB:// gpio_b
				if(num <= 23 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOC:// gpio_c
				if(num <= 15 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOD:// gpio_d
				if(num <= 9 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOE:// gpio_e
				if(num <= 11 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOX:// gpio_x
				if(num <= 35 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOY:// gpio_y
				if(num <= 15 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOZ: // gpio_Z
				if(num <= 12 && num >= 0) {
					return 0;
				}
				break;
			case PAD_GPIOAO:// gpio_ao
				if(num <= 11 && num >= 0) {
					return 0;
				}
				break;
			default:
				; // nothing
		}
		
		return last_error;
	}
}

/*
 * 
 * typedef enum {
	PAD_GPIOAO_2=184,
	PAD_GPIOB_20=150,
	PAD_GPIOAO_3=185,
	PAD_GPIOB_21=151,
	PAD_GPIOAO_4=186,
	PAD_GPIOB_22=152,
	PAD_GPIOAO_5=187,
	PAD_GPIOB_23=153,
	PAD_GPIOAO_6=188,
	PAD_GPIOAO_7=189,
	PAD_GPIOAO_8=190,
	PAD_GPIOAO_9=191,
	PAD_GPIOE_10=23,
	PAD_GPIOE_11=24,
	PAD_GPIOX_10=51,
	PAD_GPIOX_0=41,
	PAD_GPIOAO_10=192,
	PAD_GPIOX_11=52,
	PAD_GPIOX_1=42,
	PAD_GPIOAO_11=193,
	PAD_GPIOX_12=53,
	PAD_GPIOX_2=43,
	PAD_GPIOX_13=54,
	PAD_GPIOX_3=44,
	PAD_GPIOA_0=154, --------------->gpioa_base
	PAD_GPIOX_14=55,
	PAD_GPIOX_4=45,
	PAD_GPIOA_1=155,
	PAD_GPIOX_15=56,
	PAD_GPIOX_5=46,
	PAD_GPIOA_2=156,
	PAD_GPIOX_16=57,
	PAD_GPIOX_6=47,
	PAD_GPIOA_3=157,
	PAD_GPIOX_30=71,
	PAD_GPIOX_17=58,
	PAD_GPIOX_7=48,
	PAD_TEST_N=194,
	PAD_GPIOA_4=158,
	PAD_GPIOX_31=72,
	PAD_GPIOX_18=59,
	PAD_GPIOX_8=49,
	PAD_GPIOA_5=159,
	PAD_GPIOD_0=95,
	PAD_GPIOX_32=73,
	PAD_GPIOX_19=60,
	PAD_GPIOX_9=50,
	PAD_GPIOA_6=160,
	PAD_GPIOD_1=96,
	PAD_GPIOX_33=74,
	PAD_GPIOA_7=161,
	PAD_GPIOD_2=97,
	PAD_GPIOX_34=75,
	PAD_GPIOA_8=162,
	PAD_GPIOD_3=98,
	PAD_GPIOX_35=76,
	PAD_GPIOA_9=163,
	PAD_GPIOD_4=99,
	PAD_GPIOA_10=164,
	PAD_GPIOD_5=100,
	PAD_GPIOA_11=165,
	PAD_GPIOD_6=101,
	PAD_GPIOA_12=166,
	PAD_GPIOD_7=102,
	PAD_GPIOA_13=167,
	PAD_GPIOD_8=103,
	PAD_GPIOA_14=168,
	PAD_GPIOD_9=104,
	PAD_GPIOA_15=169,
	PAD_GPIOA_16=170,
	PAD_GPIOA_17=171,
	PAD_GPIOA_18=172,
	PAD_GPIOA_19=173,
	PAD_GPIOY_10=35,
	PAD_GPIOY_11=36,
	PAD_GPIOY_12=37,
	PAD_GPIOY_13=38,
	PAD_GPIOY_14=39,
	PAD_GPIOY_15=40,
	PAD_GPIOB_10=140,
	PAD_GPIOB_11=141,
	PAD_GPIOB_12=142,
	PAD_GPIOB_13=143,
	PAD_GPIOY_0=25,
	PAD_GPIOB_14=144,
	PAD_GPIOY_1=26,
	PAD_GPIOB_15=145,
	PAD_GPIOY_2=27,
	PAD_GPIOB_16=146,
	PAD_GPIOY_3=28,
	PAD_GPIOB_17=147,
	PAD_GPIOB_0=130, ---------------->gpiob_base
	PAD_GPIOY_4=29,
	PAD_GPIOB_18=148,
	PAD_GPIOB_1=131,
	PAD_GPIOY_5=30,
	PAD_GPIOB_19=149,
	PAD_GPIOB_2=132,
	PAD_GPIOY_6=31,
	PAD_GPIOB_3=133,
	PAD_GPIOY_7=32,
	PAD_GPIOB_4=134,
	PAD_GPIOY_8=33,
	PAD_GPIOB_5=135,
	PAD_GPIOY_9=34,
	PAD_GPIOE_0=13,
	PAD_GPIOB_6=136,
	PAD_GPIOZ_10=10,
	PAD_GPIOE_1=14,
	PAD_GPIOB_7=137,
	PAD_GPIOZ_11=11,
	PAD_GPIOE_2=15,
	PAD_GPIOB_8=138,
	PAD_GPIOZ_12=12,
	PAD_GPIOE_3=16,
	PAD_GPIOB_9=139,
	PAD_GPIOE_4=17,
	PAD_GPIOE_5=18,
	PAD_GPIOE_6=19,
	PAD_GPIOE_7=20,
	PAD_BOOT_0=77,
	PAD_GPIOE_8=21,
	PAD_BOOT_1=78,
	PAD_GPIOE_9=22,
	PAD_BOOT_2=79,
	PAD_BOOT_3=80,
	PAD_BOOT_4=81,
	PAD_BOOT_5=82,
	PAD_BOOT_6=83,
	PAD_GPIOX_20=61,
	PAD_GPIOC_10=115,
	PAD_BOOT_7=84,
	PAD_GPIOX_21=62,
	PAD_GPIOC_11=116,
	PAD_BOOT_8=85,
	PAD_GPIOX_22=63,
	PAD_GPIOC_12=117,
	PAD_BOOT_9=86,
	PAD_GPIOX_23=64,
	PAD_GPIOC_13=118,
	PAD_GPIOX_24=65,
	PAD_GPIOC_14=119,
	PAD_GPIOX_25=66,
	PAD_GPIOC_15=120,
	PAD_GPIOX_26=67,
	PAD_GPIOX_27=68,
	PAD_GPIOX_28=69,
	PAD_GPIOX_29=70,
	PAD_GPIOA_20=174,
	PAD_GPIOA_21=175,
	PAD_GPIOA_22=176,
	PAD_GPIOA_23=177,
	PAD_GPIOA_24=178,
	PAD_GPIOA_25=179,
	PAD_GPIOA_26=180,
	PAD_BOOT_10=87,
	PAD_GPIOA_27=181,
	PAD_BOOT_11=88,
	PAD_GPIOZ_0=0,
	PAD_BOOT_12=89,
	PAD_GPIOZ_1=1,
	PAD_CARD_0=121,
	PAD_BOOT_13=90,
	PAD_GPIOZ_2=2,
	PAD_CARD_1=122,
	PAD_BOOT_14=91,
	PAD_GPIOZ_3=3,
	PAD_CARD_2=123,
	PAD_GPIOC_0=105,
	PAD_BOOT_15=92,
	PAD_GPIOZ_4=4,
	PAD_CARD_3=124,
	PAD_GPIOC_1=106,
	PAD_BOOT_16=93,
	PAD_GPIOZ_5=5,
	PAD_CARD_4=125,
	PAD_GPIOC_2=107,
	PAD_BOOT_17=94,
	PAD_GPIOZ_6=6,
	PAD_CARD_5=126,
	PAD_GPIOC_3=108,
	PAD_GPIOZ_7=7,
	PAD_CARD_6=127,
	PAD_GPIOC_4=109,
	PAD_GPIOZ_8=8,
	PAD_CARD_7=128,
	PAD_GPIOC_5=110,
	PAD_GPIOZ_9=9,
	PAD_CARD_8=129,
	PAD_GPIOC_6=111,
	PAD_GPIOC_7=112,
	PAD_GPIOC_8=113,
	PAD_GPIOC_9=114,
	PAD_GPIOAO_0=182,
	PAD_GPIOAO_1=183,
	PAD_MAX_PADS=195
}
 * */
