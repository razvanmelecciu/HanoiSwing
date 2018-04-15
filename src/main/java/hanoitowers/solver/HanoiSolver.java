package hanoitowers.solver;

public class HanoiSolver 
{
    /// Solve the problem for the specified size
    public static String SolveProblem(int Size) 
    {
        Solver Instance = new Solver();
        Instance.SetProblemSize(Size);
        return Instance.SolveProblem();
    }   
}


class Solver
{   
    /// Ctor
    public Solver()
    {
        m_NumberDisks = 3;
        m_NoMovesPerformed = 0;
        m_Log = new String();
        m_Log = "Move log : \n";
    }
    
    // - Mutators

    /// Set the problem size
    public void SetProblemSize(int DisksNumber)
    {
        m_NumberDisks = DisksNumber;
    }
    
    /// Solve the problem
    public String SolveProblem()
    {       
        RecSolve(m_NumberDisks, "<Source>", "<Destination>", "<Aux>");
        
        m_Log += "\n Moves performed: " + GetNumberMoves();
        return GetLog();
    }
    
    /// Internal recursive function for mving disks
    private void RecSolve(int DiskToMove, String Initial, String Final, String Intermediate)
    {
        ++m_NoMovesPerformed;
        if (DiskToMove == 1)
        {
            m_Log +="\n Moving disk " + DiskToMove + " from stack " + Initial + " to stack " + Final;
            return;
        }
        RecSolve(DiskToMove - 1, Initial, Intermediate, Final);
        m_Log += "\n Moving disk " + DiskToMove + " from stack " + Initial + " to stack " + Final;
        RecSolve(DiskToMove - 1, Intermediate, Initial, Final);
    }
    
    /// Get the number of moves performed
    public int GetNumberMoves()
    {
        return m_NoMovesPerformed;
    }
    
    /// Get the log that contains the solution steps
    String GetLog()
    {
        return m_Log;
    }
    
    // - Members
    
    protected int m_NumberDisks;
    int m_NoMovesPerformed;
    String m_Log;
}

