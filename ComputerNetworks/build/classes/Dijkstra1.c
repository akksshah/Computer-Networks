#include <stdio.h>
#include <limits.h>

Int V;

int minDistance(int dist[], bool sptSet[]) {
  int min = INT_MAX, min_index;
  for (int v = 0; v < V; v++)
    if (sptSet[v] == false && dist[v] <= min) min = dist[v], min_index = v;
  return min_index;
}

void printPath(int parent[], int j) {
  if (parent[j] == -1) return;
  printPath(parent, parent[j]);
  printf("%d ", j);
}

int printSolution(int dist[], int n, int parent[]) {
  int src = 0;
  printf("Vertex\t  Distance\tPath");
  for (int i = 1; i < V; i++) {
    printf("\n%d -> %d \t\t %d\t\t%d ", src, i, dist[i], src);
    printPath(parent, i);
  }
}

void dijkstra(int graph[V][V], int src) {
  int dist[V], parent[V];
  bool sptSet[V];

  for (int i = 0; i < V; i++) {
    parent[0] = -1;
    dist[i] = INT_MAX;
    sptSet[i] = false;
  }

  dist[src] = 0;
  for (int count = 0; count < V - 1; count++) {
    int u = minDistance(dist, sptSet);
    sptSet[u] = true;
    for (int v = 0; v < V; v++)
      if (!sptSet[v] && graph[u][v] && dist[u] + graph[u][v] < dist[v]) {
        parent[v] = u;
        dist[v] = dist[u] + graph[u][v];
      }
  }
  printSolution(dist, V, parent);
}






int main() {
  printf(“Enter no of nodes: ”);
  scanf(“%d”, &V);
  int graph[V][V];
  printf(“Enter adjacency matrix:\n”);
  for(int i=0; i<V; i++){
	for(int j=0; j<V; j++){
	    scanf("%d", &graph[i][j]);
	}
  }
  dijkstra(graph, 0);
  return 0;
}