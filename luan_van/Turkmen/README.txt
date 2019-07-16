StatefulRBAC is a set of tools that generates variable sizes of RBAC instances (i.e. policies and past actions) and user authorization queries (UAQ). It then efficiently answers the queries according to optimized SAT encoding explained in [1] that builds upon the concepts presented in [2] and [3].

[Requirements ...]
StatefulRBAC uses several external tools when solving a UAQ problem and performing large-scale experimentation. The recent MaxSAT solvers from MaxSAT competition can be easily integrated to the tool. These solvers are executed from console and are stored under directory SAT_Tools. StatefulRBAC moreover needs DescriptiveStatistics (Apache Commons Math) for statistics. Thus requirements are as follows:
- SAT4J (www.sat4j.org) 
- Apache Commons Exec
- Apache Commons Math
- A MaxSAT competition solver (http://maxsat.ia.udl.cat/introduction/), by default QMaxSAT (https://sites.google.com/site/qmaxsat/) is enabled...


[Running ...]
Before compiling the tool, make sure that the MaxSAT solver you chose to use is compiled under SAT_Tools. 
In order to run the code just execute the simple shell commands available in the file COMPILE. It includes the compilation and execution of all source files. The conventional way of running the tool is to execute COMPILE with nohup as follows:
nohup COMPILE > output.txt &


[Benchmarks ...]
The tool will read the benchmark details such as role and history sizes from configuration files available in the directory "tests". There are various sample configuration files available in the directory. The results of the experiments are also stored under tests directory. Experiment type is activated in the file Tester.java which contains a switch/case statement. The desired experiment can be selected by simply uncommenting    

If you need help about the tool, just send an e-mail to fturkmen[at]gmail.com. Please use [1] and [4] for citing the work.

Thanks and enjoy!

References
[1] A. Armando, B.Crispo, S. Ranise, F. Turkmen, "Efficient run-time solving of RBAC user authorization queries: Pushing the Envelope", CODASPY'12.
[2] G. T. Wickramaarachchi, W. H. Qardaji, N. Li, "An Efficient Framework for User Authorization Queries in RBAC Systems", SACMAT'09.
[3] Y. Zhang and J. B. D. Joshi, Uaq: a framework for user authorization query processing in rbac extended with hybrid hierarchy and constraints, SACMAT'08.
[4]  A. Armando, B.Crispo, S. Ranise, F. Turkmen, "An Efficient Policy Evaluation Procedure for Constrained RBAC", Journal'13.