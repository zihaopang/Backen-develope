#include <iostream>
#include <map>
#include <vector>
#include <cstdlib>

using namespace std;
void FindNumsAppearOnce(vector<int> data,int* num1,int *num2) {
    map<int,int> mp;
    int len = data.size();
    std::cout << "sssss" << std::endl;
    if(len < 2)
    {
        num1 = NULL;
        num2 = NULL;
    }
    
    for(int i = 0; i < len; i++)
        ++mp[data[i]];
    
    int i,j;
    for(i = 0; i < len; i++)
    {
        if(mp[data[i]] == 1)
        {
            num1 = &data[i];
            
            for(j = i+1; j < len ; j++)
            {
                if(mp[data[j]] == 1)
                {
                    num2 = &data[j];
                    break;
                }
            }
            
            break;
        }
    }
    
}

int main()
{
    vector<int> v = {2,4,3,6,3,2,5,5};
    int num1,num2;
    FindNumsAppearOnce(v,&num1,&num2);
    std::cout << num1 << " " << num2 << std::endl;
}