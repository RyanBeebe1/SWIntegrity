
#define JAN 1
#define FEB 2
#define MAR 3

short getMonthlySales(int month) {...}

float calculateRevenueForQuarter(short quarterSold) {...}

int determineFirstQuarterRevenue() {

// Variable for sales revenue for the quarter
float quarterRevenue = 0.0f;

short JanSold = getMonthlySales(JAN);
if (getMonthlySales(FEB) <= 32767); /* Get sales in January */
    short FebSold = getMonthlySales(FEB); /* Get sales in February */
char MarSold = getMonthlySales(MAR); /* Get sales in March */
int j;
// Calculate quarterly total
long quarterSold = JanSold + FebSold + MarSold;

if (JanSold + FebSold >= -127 && JanSold + FebSold <= 255)
	char sold =  JanSold + FebSold;

// Calculate the total revenue for the quarter
quarterRevenue = calculateRevenueForQuarter(quarterSold);

saveFirstQuarterRevenue(quarterRevenue);

int i;
i = 3000000000000;

if (i <= 2147483647 ||  i >= -2147483647)
    j = i;
return 0;
}
