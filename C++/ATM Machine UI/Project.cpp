#include<iostream>
#include <conio.h>
#include <fstream>
#include <cstdlib>
#include <iomanip>
using namespace std;
void IDCheck(string);
void AccountCreation(string,string,string);
void printIntroMenu(char);
void printMainMenu(string);
void start();
void login();
void Authentication(string,string);
void createAccount();
void DepositMoney(string);
void WithdrawMoney(string);
void Balance(string);
void UpdateBalance(string,float);
ifstream inputInfo;
ofstream outputInfo;

int main()
{
    cout<<"Hello! Welcome to Goliath National Bank's ATM\n";
    start();

    return 0;
}

void printIntroMenu(char *option)
{
    cout<<"L = Login \nC = Create new account \nQ = Quit \n"<<endl;
    cin>>*option;
}

void printMainMenu(string ID)
{
    cout.setf(ios::fixed);
    float money;
    string username,pw1,pw2;
    inputInfo.open("userData.txt");
    do
    {
        inputInfo>>username>>pw1>>pw2>>money;
        if(ID==username)
        {
            cout<<"\nID : "<<ID<<"\nMoney in bank : RM"<< setprecision(2) <<money<<endl;
            break;
        }
    }
    while(username!="");
    inputInfo.close();

    char option;
    cout<<"Please enter the character corresponding to the service that you need \n";
    cout<<"D = Deposit Money \nW = Withdraw Money \nM = Previous Menu \nQ = Quit \n------------------> ";
    cin>>option;

     switch (option)
    {
        case 'd' :
        case 'D' : DepositMoney(ID);break;
        case 'w' :
        case 'W' : WithdrawMoney(ID);break;
        case 'm' :
        case 'M' : start();
        case 'q' :
        case 'Q' : exit(0);break;
        default  : cout<<"Invalid input!"<<endl;start();
    }
}

void DepositMoney(string ID)
{
    float money,deposit;
    string username,pw1,pw2;
    inputInfo.open("userData.txt");
    do
    {
        inputInfo>>username>>pw1>>pw2>>money;
        if(ID==username)
            break;
    }
    while(username!="");

    cout<<"How much do you want to deposit? : RM";
    cin>>deposit;
    money+=deposit;
    inputInfo.close();
    UpdateBalance(ID,money);
}

void WithdrawMoney(string ID)
{
    float money,withdraw;
    string username,pw1,pw2;
    inputInfo.open("userData.txt");
    do
    {
        inputInfo>>username>>pw1>>pw2>>money;
        if(ID==username)
            break;
    }
    while(username!="");
    do
    {
        cout<<"How much do you want to withdraw? : RM";
        cin>>withdraw;
        if(withdraw>money)
            cout<<"Insufficient amount of funds in the bank. :(  Try again.\n";
    }
    while(withdraw>money);
    money-=withdraw;
    inputInfo.close();
    UpdateBalance(ID,money);
}

void UpdateBalance(string ID,float balance)
{
    int i=0;
    float money;
    string username,pw1,pw2,tempcheck;
    inputInfo.open("userData.txt");
    do
    {
        inputInfo>>username>>pw1>>pw2>>money;
        if(tempcheck==username)
        {
            i++;
            break;
        }
        i++;
        tempcheck=username;
    }
    while(username!="");
    inputInfo.close();
    string Data[i][4];
    float Money[i];
    inputInfo.open("userData.txt");
    for(int j=0;j<i;j++)
    {
        inputInfo>>username>>pw1>>pw2>>money;
        Data[j][0]=username;
        Data[j][1]=pw1;
        Data[j][2]=pw2;
        Money[j]=money;
        if(username==ID)
            Money[j]=balance;
    }

    inputInfo.close();

    outputInfo.open("userData.txt");
    for(int j=0;j<(i-1);j++)
    {
        outputInfo<<Data[j][0]<<" "<<Data[j][1]<<" "<<Data[j][2]<<" "<<Money[j]<<endl;
    }
    outputInfo.close();
    printMainMenu(ID);
}

void start()
{
    char option;
    cout<<"Please select an option from the menu below : "<<endl;
    printIntroMenu(&option);

    switch (option)
    {
        case 'l' :
        case 'L' : login();break;
        case 'c' :
        case 'C' : createAccount();break;
        case 'q' :
        case 'Q' : exit(0);break;
        default  : cout<<"Invalid input!"<<endl;start();
    }
}

void login()
{
    string ID,Password;
    cout<<"- Login -\n";
    cout<<"Please enter your ID : ";
    cin>>ID;
    cout<<"Please enter your password : ";
    cin>>Password;

    Authentication(ID,Password);
}

void Authentication(string ID,string PW)
{
    float money;
    string name,tempname,password,scndpassword;
    inputInfo.open("userData.txt");
    cout<< "Reading from the database..." << endl;
    do
    {
        inputInfo>> name>>password>>scndpassword>>money;
        if(name==ID&&password==PW)
        {
            cout<<"Access Granted!"<<endl;
            inputInfo.close();
            printMainMenu(ID);
            break;
        }
        if(name==ID&&password!=PW)
        {
            cout<<"Wrong password!Try Again\n"<<endl;
            inputInfo.close();
            start();
            break;
        }
        if(tempname==name)
        {
            cout<<"\nNo such user is found in database\n";
            inputInfo.close();
            start();
            break;
        }
        tempname=name;
    }
    while(name!=ID);
}

void createAccount()
{
    string ID,PW1,PW2,PW3;
    cout<<"- Create Account - \n"<<endl;
    cout<<"Please enter the ID for your new account : ";
    cin>>ID;
    IDCheck(ID);

    do
    {
        cout<<"Please enter the primary password for your new account : ";
        cin>>PW1;
        cout<<"Please re-enter the primary password for your new account : ";
        cin>>PW2;

        if(PW1!=PW2)
            cout<<"Sorry. The second primary password doesnt match with the first one."<<endl<<endl;
    }
    while(PW1!=PW2);

    do
    {
        cout<<"( for emergency purposes )\nPlease enter the secondary password for your new account  : ";
        cin>>PW2;
        cout<<"Please re-enter the secondary password for your new account : ";
        cin>>PW3;

        if(PW2!=PW3)
            cout<<"Sorry. The second secondary password doesnt match with the first one."<<endl<<endl;
    }
    while(PW2!=PW3);


    AccountCreation(ID,PW1,PW2);
    cout<<"Thank you! Your account has been created!\n"<<endl;
    start();
}
void IDCheck(string ID)
{
    float money;
    string username,pw1,pw2,tempcheck;
    inputInfo.open("userData.txt");
    do
    {
        inputInfo>>username>>pw1>>pw2>>money;
        if(ID==username)
        {
            cout<<"Sorry. The username is used by other account. Please enter different username.\n\n";
            inputInfo.close();
            createAccount();
            break;
        }
        if(tempcheck==username)
        {
            inputInfo.close();
            break;
        }
        tempcheck=username;
    }
    while(username!="");
}

void AccountCreation(string ID,string PW1,string PW2)
{
    int i=0;
    float money;
    string username,pw1,pw2,tempcheck;
    inputInfo.open("userData.txt");
    do
    {
        inputInfo>>username>>pw1>>pw2>>money;
        if(tempcheck==username)
        {
            i++;
            break;
        }
        i++;
        tempcheck=username;
    }
    while(username!="");
    inputInfo.close();
    string Data[i][4];
    float Money[i];
    inputInfo.open("userData.txt");
    for(int j=0;j<i;j++)
    {
        inputInfo>>username>>pw1>>pw2>>money;
        Data[j][0]=username;
        Data[j][1]=pw1;
        Data[j][2]=pw2;
        Money[j]=money;
    }

    Data[i-1][0]=ID;
    Data[i-1][1]=PW1;
    Data[i-1][2]=PW2;
    Money[i-1]=0;
    inputInfo.close();

    outputInfo.open("userData.txt");
    for(int j=0;j<i;j++)
    {
        outputInfo<<Data[j][0]<<" "<<Data[j][1]<<" "<<Data[j][2]<<" "<<Money[j]<<endl;
    }
    outputInfo.close();
}
