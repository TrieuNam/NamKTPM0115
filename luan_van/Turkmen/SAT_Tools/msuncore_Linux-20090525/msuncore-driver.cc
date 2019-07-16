/*----------------------------------------------------------------------------*\
 * File:        msuncore-driver.cc
 *
 * Description: Example driver for MSUnCore.
 *
 * Author:      jpms
 * 
 *                                     Copyright (c) 2009, Joao Marques-Silva
\*----------------------------------------------------------------------------*/

#include <iostream>

#include "include/msuncore.hh"

using namespace std;


/*----------------------------------------------------------------------------*\
 * Purpose: Example main to illustrate use of MSUnCore API.
\*----------------------------------------------------------------------------*/

int main(int argc, char** argv)
{
  MSUnCore& msuapp = MSUnCore::instance();
  msuapp.init();
  msuapp.set_runtime_defaults();
  msuapp.load_file(argv[1]);
  msuapp.solve();
  long int mincost = msuapp.get_min_unsat_cost();
  if (mincost >= 0) {
    cout << "o " << mincost << endl;
    cout << "s OPTIMUM FOUND\n";
  } else { cout << "s UNSATISFIABLE\n"; }

  cout << "v ";
  vector<int> model;
  msuapp.get_model(model);
  vector<int>::iterator mpos = model.begin();
  for(int i = 1; mpos != model.end(); ++mpos, ++i) {
    if (i > msuapp.get_num_init_vars()) { break; }
    if (*mpos > 0)      { cout << i << " "; }
    else if (*mpos < 0) { cout << -i << " "; }
  }
  cout << endl;
  msuapp.prt_all_unsat_clauses();
  msuapp.reset();
  return 0;
}

/*----------------------------------------------------------------------------*/
