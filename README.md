# 🧪 Test Case Prioritisation

This project applies **meta-heuristic optimization algorithms** to solve the **regression test case prioritisation** problem. The goal is to reorder test cases in a regression suite so that faults in the software are revealed as early as possible.

---

## 📌 Problem Statement

In regression testing, rerunning all test cases is often impractical due to limited time and resources. To optimize this process, we prioritise the execution order of test cases.

Formally, the problem is defined as follows:

- **Given**:
  - A test suite `T` of `n` test cases
  - All permutations `Π(T)` of `T`
  - A fitness function `f: Π(T) → ℝ`

- **Objective**:  
  Find `T* ∈ Π(T)` such that for all `T′ ∈ Π(T)`, `f(T′) ≥ f(T*)`

This project implements the following algorithms to tackle this NP-hard problem:
- Random Search (RS)  
- Random Walk (RW)  
- Simulated Annealing (SA)  
- Genetic Algorithm (GA)  

---

## 🧬 Solution Encoding

Test cases are represented as a permutation of integers from `0` to `n-1`.

**Example**: `[2, 4, 1, 0, 3]`  
This means test case `2` is run first, test case `4` second, and so on.

---

## 🔧 Implemented Components

- ✅ **Solution Encoding**  
  - `TestOrder`: Ensures valid permutations  
  - `TestOrderGenerator`: Creates random valid permutations

---

## 🔁 Operators

- `ShiftToBeginningMutation`: Moves a random test case to the start  
- `OrderCrossover`: Order-based crossover (OX1)  
- `TournamentSelection`: Selects parents for GA reproduction  

---

## 📈 Fitness Function

- **APLC**: Average Percentage of Line Coverage  
  - Higher APLC → Better prioritisation

---

## ⏱️ Stopping Condition

- `MaxFitnessEvaluations`: Stops the algorithm after a fixed number of fitness evaluations

---

## ❄️ Simulated Annealing Details

- SA adapts APLC for minimisation by negating its value  
- Initial temperature `τ₀` is based on the average energy difference from a random walk:
  - Perform `n` random walk steps to compute average `ΔE`
  - Use default acceptance probability `p₀ = 0.5`
  - Compute `τ₀` from: `p₀ = exp(-ΔE / τ₀)`
- Cooling schedule: `τ(k+1) = 0.9 * τ(k)`
- Equilibrium is reached after `N_accept = 12 * N` accepted moves
