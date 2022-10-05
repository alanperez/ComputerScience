

#include <string>
#include <algorithm>
#include <iostream>
#include <fstream>
#include <vector>


using namespace std;

//*	a function to find the sum of a numeric vector
double sum(vector<double> vect) {
    double sum = 0;
//    loop through as long its less than the vectors size.
    for (int i = 0; i < vect.size(); i++) {
//       store the results into sum
        sum = vect[i] + sum;
    }
    return sum;
}

//*	a function to find the mean of a numeric vector
double mean(vector<double> vect) {
//    find the mean by invoking the sum funct and dividing by the size
    return sum(vect)/vect.size();
}
//*	a function to find the median of a numeric vector
double median(vector<double> vect) {
    double median = 0;
    sort(vect.begin(), vect.end());
//
    if(vect.size() % 2 != 0) {
        median = vect[vect.size() / 2];
    }
    else {
//      averages the two middle elements
        median = (vect[(vect.size() -1) / 2] + vect[vect.size() / 2]) /2;
    }
    return median;
}
//*	a function to find the range of a numeric vector
double max(vector<double> vect) {
//    max value
    double max = *max_element(vect.begin(), vect.end());
    return max;
}
double min(vector<double> vect) {
//    minimum value
    double min = *min_element(vect.begin(), vect.end());
    return min;
}

//*	a function to compute covariance between rm and medv (see formula on p. 74 of pdf)
double covariance(vector<double> rmVect, vector<double> medvVect) {
//    store the mean values to var
    double rmvector = mean(rmVect);
    double medvector = mean(rmVect);
    double res = 0;

    for (int i = 0; i < rmVect.size(); i++) {
//        subtract vectors and multiply
        res += (rmVect[i] - rmvector) * (medvVect[i] - medvector);
    }
//    return result
    return res / (rmVect.size() - 1);
}

int main() {

    ifstream inFS;     // Input file stream
    string line;
    string rm_in, medv_in;
    const int MAX_LEN = 1000;
    vector<double> rm(MAX_LEN);
    vector<double> medv(MAX_LEN);

    // Try to open file
    cout << "Opening file Boston.csv." << endl;

    inFS.open("Boston.csv");
    if (!inFS.is_open()) {
        cout << "Could not open file Boston.csv." << endl;
        return 1; // 1 indicates error
    }

    // Can now use inFS stream like cin stream
    // Boston.csv should contain two doubles

    cout << "Reading line 1" << endl;
    getline(inFS, line);

    // echo heading
    cout << "heading: " << line << endl;

    int numObservations = 0;
    while (inFS.good()) {

//        Read data
        getline(inFS, rm_in, ',');
        getline(inFS, medv_in, '\n');

        rm.at(numObservations) = stof(rm_in);
        medv.at(numObservations) = stof(medv_in);

        numObservations++;
    }

    rm.resize(numObservations);
    medv.resize(numObservations);

// PRINTING
//3	Call the functions described in a-d for rm and for medv. Call the covariance and correlation functions. Print results for each function.
    cout << "new length " << rm.size() << endl;
    cout << "medv length " << medv.size() <<endl;
    cout << "Closing file Boston.csv." << endl;


    inFS.close(); // Done with file, so close it

    cout << "Number of records: " << numObservations << endl;
    cout << "RM: " << sum(rm) << endl;
    cout << "MEDV: " << sum(medv) << endl;
    cout << "==========" << endl;
    cout << endl;

    cout << " - Calculating the MEAN - " << endl;
    cout << "rm mean: " << mean(rm) << endl;
    cout << "medv mean: " << mean(medv) << endl;
    cout << endl;

    cout << "- Calculating the MEDIAN -" << endl;
    cout << "Rooms Median: " << median(rm) << endl;
    cout << "Median for Median Value: " << median(medv) << endl;
    cout << endl;

    cout << "- Calculating the RANGE -" << endl;
    cout << "Range for ROOMS: " << "minimum: "<< min(rm) << " maxiumum: "<< max(rm) << endl;
    cout << "Median Value Range from MIN TO MAX: " << "MIN:  " << min(medv) << " MAX:  " << max(medv) << endl;
    cout << endl;


    cout << " - Calculating the COVARIANCE - " << endl;
    cout << "Covariance : " << covariance(rm,medv) << endl;
    cout << endl;

    cout << " - Calculating the CORRELEATION - " << endl;
    cout << "Correlation : " << " :( " << endl;
    cout << endl;

}

