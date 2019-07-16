### Makefile --- 

## Author:   jpms
## Keywords: 
## Revision: $Id$.
## Copyright (c) 2009, Joao Marques-Silva

CXX        = g++
CXXFLAGS   = -O3
MSUMAIN    = msuncore-driver
TRGT       = msuncore

all: $(TRGT)

$(TRGT): $(MSUMAIN).o
	g++ -o $(TRGT) $(MSUMAIN).o -L./lib -lm -lz -lmsuncore

clean:
	rm *.o $(TRGT)

%.o:     %.cc
	$(CXX) $(CXXFLAGS) $(CPPFLAGS) -c -o $@ $<

### Makefile ends here
