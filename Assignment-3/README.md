# OOP_2

___

## Part 1

___
In this part we compare 3 methods of calculating the combined number of lines of multiple txt files:

#### Files creator

```
    public static String[] createTextFiles(int n, int seed, int bound)
```

A method to create *n* txt files, each with a random number of lines.

### Iterative approach

```
    public static int getNumOfLines(String[] fileNames)
```

Loop over each file and count its lines.

### Threads

```
    public static int getNumOfLinesThreads(String[] fileNames)
```

Using the *class* ***LineCounter*** we built, which extents the *class* ***Thread***, we were able to calculate the
number of lines by assigning a thread to calculate the number of lines in each file.

### ThreadPool

```
    public static int getNumOfLinesThreadPool(String[] fileNames)
```

Using the *class* ***LineCounterPool*** we built, which implements the *interface* ***Callable***, we create a
LineCounterPool object for each file and submit it to an executor service (a thread pool).
The **OVERRIDDEN** *call()* method counts the number of line in the file assigned to it, and waits for the results of
the Callable tasks and returns the total number of lines in all the files.

### How to run

___
After running `EX2_1.java` you'll be asked to enter first *n* (number of files to create), and then *bound* (max
number of lines in each file).
<br>
After entering each number, press `ENTER`, and the program will start generating the files and running all 3 methods on
them.
<br>
When it finishes all 3 methods the program will delete all the files it created.

### Results Comparison

|     | Num of files | Bound  | Total num of lines | *Iterative* | *Threads* | *ThreadPool* |
|-----|--------------|--------|--------------------|-------------|-----------|--------------|
| 1   | 10           | 10     | 41                 | 18 ms       | 6 ms      | 9 ms         |
| 2   | 100          | 50     | 2,730              | 171 ms      | 28 ms     | 11 ms        |
| 3   | 100          | 100    | 5,380              | 211 ms      | 21 ms     | 15 ms        | 
| 4   | 1,000        | 500    | 247,226            | 2,629 ms    | 91 ms     | 106 ms       |
| 5   | 10,000       | 1,000  | 5,031,925          | 38,324 ms   | 820 ms    | 1,024 ms     |
| 6   | 10,000       | 5,000  | 25,195,925         | 87,420 ms   | 2,291 ms  | 5,863 ms     |
| 7   | 50,000       | 10,000 | 249,777,821        | 404,677 ms  | 12,281 ms | 103,469 ms   |
| 8   |              |        |                    |             |           |              |

### UML Diagram for Part 1

![Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/GalHillel/OOP_2/main/UML/EX2_1_UML.plantuml)

## Part 2

___
We have created a system in which we can use ThreadPool that prioritize the operation it executes (and not only its
Thread).

### Project Structure

___

#### TaskType (Enum)

The tasks type representing its priority:

* For Computational Tasks, we'll assign: `COMPUTATIONAL` (Value = 1)
* For IO-Bound Tasks, we'll assign: `IO` (Value = 2)
* For Unknown Tasks, we'll assign: `OTHER` (Value = 3)

#### Task<V>

Implementing the *Interfaces* ***Comparable*** and ***Callable***, a `Task<V>` object represents a task with a TaskType
and may return a value of some type.<br>
A `Task<V>` object can be *compared* to other `Task<V>` object by its `TaskType` parameter (`OTHER` if not defined).

#### MyFutureTask

Extends ***FutureTask*** and implements ***Comparable***, a `MyFutureTask` object wraps a `Task` so it can enter
the `PriorityBlockingQueue`.

#### CustomExecutor

Our Prioritized ThreadPool is an Executor that asynchronously computes `Task<V>` instances.<br>
`CustomExecutor` maintains the maximum priority of Task instances in the queue at any given time.

After finishing of all tasks submitted to the executor, the `CustomExecutor`will terminate the executor.

### UML Diagram of Part 2

![Class Diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/GalHillel/OOP_2/main/UML/EX2_2_diagram.plantuml)

## Authors

___
Roey Feingold https://github.com/RF555

Gal Hilell https://github.com/GalHillel