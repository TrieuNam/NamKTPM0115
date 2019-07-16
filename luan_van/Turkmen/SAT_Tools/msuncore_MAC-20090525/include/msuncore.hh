/*----------------------------------------------------------------------------*\
 * File:        msuncore.hh
 *
 * Description: API to use MSUncore from other applications.
 *
 * Author:      jpms
 * 
 *                                     Copyright (c) 2009, Joao Marques-Silva
\*----------------------------------------------------------------------------*/

#ifndef _MSUNCORE_H
#define _MSUNCORE_H 1

#include <cstddef>
#include <cstdlib>
#include <cassert>
#include <climits>
#include <vector>

using namespace std;


/*----------------------------------------------------------------------------*\
 * Types used by MSUncore API.
\*----------------------------------------------------------------------------*/

typedef long int LINT;
typedef unsigned long int ULINT;

class MSUVar;
typedef MSUVar* VarPtr;

class MSUCl;
typedef MSUCl* ClPtr;


/*----------------------------------------------------------------------------*\
 * Class: MSUnCore
 *
 * Purpose: Class that defines the API to use MSUncore.
 *          Use the MSUnCore class to configure, set up and run MSUncore.
\*----------------------------------------------------------------------------*/

class MSUnCore {
  friend class MSUnCoreExtended;

public:
  /* *** A. Access to MSUnCore object *** */

  static MSUnCore& instance();

  /* *** B. Initialization/Cleanup of MSUncore *** */

  virtual void init() = 0;                  // Activate internal data structures
  virtual void reset() = 0;                  // Cleanup internal data structures

  /* *** C. MSUncore configuration *** */

  // 1. Type of problem to solve: Plain MaxSAT, Partial, Weighted, Soft, etc.
  virtual void set_prob_plain_maxsat() = 0;
  virtual void set_prob_partial_maxsat() = 0;
  virtual void set_prob_weighted_maxsat() = 0;
  virtual void set_prob_soft_cnf() = 0;

  // 2. Configure the MSUncore algorithm to use, either one of the existing
  //    releases or an available combination
  // Releases: "1.0", "1.1", "1.2", "2.0", "3.0", "3.1", "4.0"
  virtual void set_release(const char* nrel) = 0;
  // Algorithms: "1", "2", "3", "3.1", "4"
  virtual void set_algorithm(const char* nalg) = 0;
  // Encodings: "a", "b", "i", "c", "e", "s", "n", "l"
  virtual void set_card_encoding(const char* enc) = 0;
  // Whether to constrain the relaxation variables of a clause
  virtual void set_relate_relax_vars(int thres) = 0;
  // Whether to use additional clauses (option -p)
  virtual void set_use_added_cls() = 0;
  // Whether to run BMO mode.
  // Note: Do not use this option unless you know what it means!
  virtual void set_bmo_mode() = 0;

  // 3. Indicate whether to enumerate models
  virtual void set_enumerate_models() = 0;         // Enumerate all models/cubes
  virtual void set_enumerate_models(LINT ncubes) = 0;  // Enumerate ncubes cubes
  virtual bool get_enum_models() = 0;              // TRUE if enumerating models
  virtual void set_enum_orig_vars() = 0;          // Use original vars for goods
  virtual void set_enum_relax_vars() = 0;       // Use relaxation vars for goods

  /* *** D. Build internal data structures *** */

  // 1. Load instance from file
  // Accepted formats: CNF, WCNF PMCNF, SCNF
  virtual bool load_file(const char* fname) = 0;

  // 2. Add clauses to MSUncore
  virtual ClPtr add_clause(LINT nlits, const LINT lits[]) = 0;
  virtual ClPtr add_clause(vector<LINT>& lits) = 0;
  virtual ClPtr add_unit_clause(LINT lit1) = 0;
  virtual ClPtr add_binary_clause(LINT lit1, LINT lit2) = 0;
  virtual ClPtr add_ternary_clause(LINT lit1, LINT lit2, LINT lit3) = 0;

  // 3. Specify clause type/weight: weight, hard, soft, soft cl group
  virtual void set_clause_hard(ClPtr cl) = 0;
  virtual void set_clause_soft(ClPtr cl) = 0;
  virtual void set_clause_soft_group(ClPtr cl, LINT clgrp) = 0;
  virtual void set_clause_weight(ClPtr cl, LINT weight) = 0;

  /* *** E. Run MSUncore *** */

  // 1. Invoke MSUncore
  // Note: Outcome can be true (solution found), or false (solution not found)
  virtual bool solve() = 0;        // Single run of MSUncore
  // Using MSUnCore for model enumeration
  virtual bool iter_solve() = 0;   // Multiple runs of MSUncore

  // 2. Extract solution values, models, var values, etc.
  virtual int get_num_init_vars() = 0;        // Get number of initial variables
  virtual int get_num_init_cls() = 0;           // Get number of initial clauses
  virtual int get_min_unsat_cost() = 0;          // Return min cost of unsat cls
  virtual int get_var(LINT varid) = 0;              // Return value of var varid
  virtual void get_model(vector<int>& smodel) = 0;      // Access computed model
  virtual void get_relaxation_vars(vector<LINT>& rvars) = 0;   // Get relax vars
  virtual bool chk_unsat_clause(ClPtr cl) = 0;  // Check whether clause is unsat
  virtual void prt_all_unsat_clauses() = 0;

  // 3. Runtime configuration
  virtual void set_runtime_defaults() = 0;                 // Set default config
  virtual void set_compute_model() = 0;                // Record computed models
  virtual void unset_compute_model() = 0;
  virtual bool get_compute_model() = 0;
  virtual void set_timeout(LINT tout) = 0;                        // Set timeout
  virtual void set_verbosity(int verb) = 0;                     // Set verbosity
  virtual int get_verbosity() = 0;
  virtual void set_logging() = 0;                            // Activate logging
  virtual bool get_logging() = 0;
  virtual void set_comp_format() = 0;        // Output MaxSAT competition format
  virtual void unset_comp_format() = 0;      // Do not output MaxSAT comp format
  virtual bool get_comp_format() = 0;
  virtual void set_lst_unsat_cls() = 0;
  virtual void unset_lst_unsat_cls() = 0;
  virtual bool get_lst_unsat_cls() = 0;

  // 4. Print stats
  virtual void print_stats() = 0;                        // Print msuncore stats

public:
  virtual ~MSUnCore() { }                                  // Virtual destructor

private:
  MSUnCore() { }                                          // Private constructor
};

#endif /* _MSUNCORE_H */

/*----------------------------------------------------------------------------*/
