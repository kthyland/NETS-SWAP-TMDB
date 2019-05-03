figure;
plotter = scatter3(GOTepisodeVISUAL.Episode1, GOTepisodeVISUAL.Episode2, GOTepisodeVISUAL.Cos_Sim);
title 'Game of Thrones Episode Cosine Similarity';
xlabel 'Episode1';
ylabel 'Episode2';
zlabel 'Cosine Similarity';
axis([1 7.1 1 7.1 0 0.5]);
view([-45 -45 5]);
