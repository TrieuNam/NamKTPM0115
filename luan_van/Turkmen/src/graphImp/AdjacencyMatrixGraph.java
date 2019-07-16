package graphImp;

public class AdjacencyMatrixGraph {

    private boolean adjMatrix[][];
    private int vCount;
	
    
    enum VertexState {
        White, Gray, Black 
    }
    
    public AdjacencyMatrixGraph(int vertexCount) {
        this.vCount = vertexCount;
        adjMatrix = new boolean[vertexCount][vertexCount];
    }
	
	 
    public void addEdge(int i, int j) {
          if (i >= 0 && i < vCount && j > 0 && j < vCount) {
                adjMatrix[i][j] = true;
                adjMatrix[j][i] = true;
          }
    }

    public void removeEdge(int i, int j) {
          if (i >= 0 && i < vCount && j > 0 && j < vCount) {
                adjMatrix[i][j] = false;
                adjMatrix[j][i] = false;
          }
    }


    public boolean isEdge(int i, int j) {
	            if (i >= 0 && i < vCount && j > 0 && j < vCount)
	                  return adjMatrix[i][j];
	            else
	                  return false;
	}


   
    public void DFS()
    {
          VertexState state[] = new VertexState[vCount];
          for (int i = 0; i < vCount; i++)
                state[i] = VertexState.White;
          runDFS(0, state);
    }
   
    
    public void runDFS(int u, VertexState[] state)
    {
          state[u] = VertexState.Gray;
          for (int v = 0; v < vCount; v++)
                if (isEdge(u, v) && state[v] == VertexState.White)
                      runDFS(v, state);
          state[u] = VertexState.Black;
    }

}
	
