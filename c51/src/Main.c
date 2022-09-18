#include<intrins.h>
#include<reg51.h>
#include<stdio.h>
sbit active=P3^0;
sbit end=P3^1;
unsigned int flag=0;
unsigned int flagCount=999;
unsigned int timeCount=10000;
unsigned int sleep=10000;

unsigned int time(unsigned int flag){
    unsigned int time = 0;
    while(time < timeCount){
        time++;
    }
    active=~active;
    flag++;
    return flag;
}

void main(){
	active = 0;
	end = 1;
    while(flag < flagCount){
       flag = time(flag);
    }
    end = 0;
    while(1){}
}
